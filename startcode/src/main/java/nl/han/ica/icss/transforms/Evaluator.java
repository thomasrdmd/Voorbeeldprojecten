package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.implementations.HANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        //variableValues = new HANLinkedList<>();
        variableValues.insert(0,new HashMap<>());
        for (ASTNode node: ast.root.getChildren()) {
            if (node instanceof VariableAssignment) {
                insertNewVariable(variableValues.getSize(), (VariableAssignment) node);
            }
            else if(node instanceof Stylerule){
                evaluateStylerule((Stylerule) node);
            }
        }
    }

    private void evaluateStylerule(Stylerule stylerule){
        for (ASTNode node: stylerule.getChildren()){
            if(node instanceof Declaration){
                setDeclarationValuesToLiterals((Declaration) node);
            }
            else if(node instanceof IfClause){
                for(Declaration declaration: setIfClauseToDeclarations((IfClause) node)){
                    stylerule.addChild(declaration);
                }
                stylerule.removeChild(node);
            }
        }
    }

    private void insertNewVariable(int scopePos, VariableAssignment var) {
        if(!(var.expression instanceof Literal)){
            setVariableAssignmentToLiteral(var);
        }
        variableValues.get(scopePos).put(var.name.name, (Literal) var.expression);
    }

    private void setVariableAssignmentToLiteral(VariableAssignment varAssign){
        if(varAssign.expression instanceof VariableReference){
            varAssign.expression = getVariableLiteral(((VariableReference) varAssign.expression).name);
        }
    }

    private Literal getVariableLiteral(String varName){
        return variableValues.get(1).get(varName);
    }

    private void setDeclarationValuesToLiterals(Declaration declaration){
        if(declaration.expression instanceof VariableReference){
            declaration.expression = getVariableLiteral(((VariableReference) declaration.expression).name);
        }
        else if(declaration.expression instanceof Operation){
            declaration.expression = setOperationToLiteral((Operation) declaration.expression);
        }
    }

    private Literal setOperationToLiteral(Operation operation){
        Literal sumTotal = null;
        if(operation instanceof MultiplyOperation) {
            if(operation.rhs instanceof Operation){
                ((Operation) operation.rhs).lhs = doMultiplyOperation(operation);
                sumTotal = setOperationToLiteral((Operation) operation.rhs);
            }
            else {
                sumTotal = doMultiplyOperation(operation);
            }
        }
        else if(operation instanceof AddOperation){
            sumTotal = doAddOperation(operation);
        }
        else if(operation instanceof SubtractOperation){
            sumTotal = doSubtractOperation(operation);
        }
        return sumTotal;
    }

    private Literal doMultiplyOperation(Operation operation) {
        Literal subTotal = null;

        Literal lhsLiteral;
        if(operation.lhs instanceof VariableReference){
            lhsLiteral = getVariableLiteral(((VariableReference) operation.lhs).name);
        }
        else {
            lhsLiteral = (Literal) operation.lhs;
        }

        Literal rhsLiteral;
        if(operation.rhs instanceof Operation) {
            rhsLiteral = (Literal) ((Operation) operation.rhs).lhs;
        }
        else if (operation.rhs instanceof VariableReference){
            rhsLiteral = getVariableLiteral(((VariableReference) operation.rhs).name);
        }
        else{
            rhsLiteral = (Literal) operation.rhs;
        }

        switch (lhsLiteral.getClass().getSimpleName()){
            case "PercentageLiteral":
                subTotal = new PercentageLiteral(((PercentageLiteral) lhsLiteral).value * ((ScalarLiteral) rhsLiteral).value);
                break;
            case "PixelLiteral":
                subTotal = new PixelLiteral(((PixelLiteral) lhsLiteral).value * ((ScalarLiteral) rhsLiteral).value);
                break;
            case "ScalarLiteral":
                switch (rhsLiteral.getClass().getSimpleName()){
                    case "PercentageLiteral":
                        subTotal = new PercentageLiteral(((ScalarLiteral) lhsLiteral).value * ((PercentageLiteral) rhsLiteral).value);
                        break;
                    case "PixelLiteral":
                        subTotal = new PixelLiteral(((ScalarLiteral) lhsLiteral).value * ((PixelLiteral) rhsLiteral).value);
                        break;
                    case "ScalarLiteral":
                        subTotal = new ScalarLiteral(((ScalarLiteral) lhsLiteral).value * ((ScalarLiteral) rhsLiteral).value);
                        break;
                }
                break;
        }
        return subTotal;
    }

    private Literal doAddOperation(Operation operation){
        Literal subTotal = null;

        Literal lhsLiteral;
        if(operation.lhs instanceof VariableReference){
            lhsLiteral = getVariableLiteral(((VariableReference) operation.lhs).name);
        }
        else {
            lhsLiteral = (Literal) operation.lhs;
        }

        Literal rhsLiteral;
        if(operation.rhs instanceof Operation) {
            rhsLiteral = setOperationToLiteral((Operation) operation.rhs);
        }
        else if (operation.rhs instanceof VariableReference){
            rhsLiteral = getVariableLiteral(((VariableReference) operation.rhs).name);
        }
        else{
            rhsLiteral = (Literal) operation.rhs;
        }

        switch (lhsLiteral.getClass().getSimpleName()){
            case "PercentageLiteral":
                subTotal = new PercentageLiteral(((PercentageLiteral) lhsLiteral).value + ((PercentageLiteral) rhsLiteral).value);
                break;
            case "PixelLiteral":
                subTotal = new PixelLiteral(((PixelLiteral) lhsLiteral).value + ((PixelLiteral) rhsLiteral).value);
                break;
            case "ScalarLiteral":
                subTotal = new ScalarLiteral(((ScalarLiteral) lhsLiteral).value + ((ScalarLiteral) rhsLiteral).value);
                break;
        }

        return subTotal;
    }

    private Literal doSubtractOperation(Operation operation){
        Literal subTotal = null;

        Literal lhsLiteral;
        if(operation.lhs instanceof VariableReference){
            lhsLiteral = getVariableLiteral(((VariableReference) operation.lhs).name);
        }
        else {
            lhsLiteral = (Literal) operation.lhs;
        }

        Literal rhsLiteral;
        if(operation.rhs instanceof Operation) {
            rhsLiteral = setOperationToLiteral((Operation) operation.rhs);
        }
        else if (operation.rhs instanceof VariableReference){
            rhsLiteral = getVariableLiteral(((VariableReference) operation.rhs).name);
        }
        else{
            rhsLiteral = (Literal) operation.rhs;
        }

        switch (lhsLiteral.getClass().getSimpleName()){
            case "PercentageLiteral":
                subTotal = new PercentageLiteral(((PercentageLiteral) lhsLiteral).value - ((PixelLiteral) rhsLiteral).value);
                break;
            case "PixelLiteral":
                subTotal = new PixelLiteral(((PixelLiteral) lhsLiteral).value - ((PixelLiteral) rhsLiteral).value);
                break;
            case "ScalarLiteral":
                subTotal = new ScalarLiteral(((ScalarLiteral) lhsLiteral).value - ((ScalarLiteral) rhsLiteral).value);
                break;
        }

        return subTotal;
    }

    private ArrayList<Declaration> setIfClauseToDeclarations(IfClause clause){
        variableValues.insert(variableValues.getSize(), new HashMap<>());
        ArrayList<Declaration> declarations = new ArrayList<>();

        BoolLiteral conditionalExpression;
        if(clause.getConditionalExpression() instanceof VariableReference){
            conditionalExpression = (BoolLiteral) getVariableLiteral(((VariableReference) clause.getConditionalExpression()).name);
        }
        else{
            conditionalExpression = (BoolLiteral) clause.getConditionalExpression();
        }

        if(conditionalExpression.value){
            for(ASTNode child: clause.getChildren()){
                if(child instanceof IfClause){
                    declarations.addAll(setIfClauseToDeclarations((IfClause) child));
                }
                else if(child instanceof VariableAssignment){
                    insertNewVariable(variableValues.getSize(), (VariableAssignment) child);
                }
                else if(child instanceof Declaration){
                    declarations.add((Declaration) child);
                }
            }
        }
        else if(clause.getElseClause() != null){
            for(ASTNode child: clause.getElseClause().getChildren()){
                if(child instanceof IfClause){
                    declarations.addAll(setIfClauseToDeclarations((IfClause) child));
                }
                else if(child instanceof VariableAssignment){
                    insertNewVariable(variableValues.getSize(), (VariableAssignment) child);
                }
                else if(child instanceof Declaration){
                    declarations.add((Declaration) child);
                }
            }
        }

        //Om variabelen die hier uit de scope gaan worden gegooit om te zetten, en gelijk maar berekeningen uit te voeren.
        for(Declaration declaration: declarations){
            setDeclarationValuesToLiterals(declaration);
        }

        variableValues.delete(variableValues.getSize());
        return declarations;
    }
}
