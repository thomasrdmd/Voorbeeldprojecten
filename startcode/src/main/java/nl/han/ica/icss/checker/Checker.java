package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.implementations.HANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;


public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        variableTypes.insert(0,new HashMap<>());
        for (ASTNode node: ast.root.getChildren()) {
            if(node instanceof VariableAssignment){
                insertNewVariable(variableTypes.getSize(), (VariableAssignment) node);
            }
            else if(node instanceof Stylerule){
                checkStyleRule((Stylerule) node);
            }
        }

    }

    public ExpressionType getType(Expression expression){
        switch (expression.getClass().getSimpleName()){
            case "BoolLiteral":
                return ExpressionType.BOOL;

            case "ColorLiteral":
                return ExpressionType.COLOR;

            case "PercentageLiteral":
                return ExpressionType.PERCENTAGE;

            case "PixelLiteral":
                return ExpressionType.PIXEL;

            case "ScalarLiteral":
                return ExpressionType.SCALAR;

            case "VariableReference":
                return getVariableType(((VariableReference) expression).name);

            case "AddOperation":
            case "SubtractOperation":
            case "MultiplyOperation":
                if(((Operation)expression).lhs instanceof Literal || ((Operation)expression).lhs instanceof VariableReference){
                    return getType(((Operation)expression).lhs);
                }
                else if(((Operation)expression).rhs instanceof Literal || ((Operation)expression).rhs instanceof VariableReference){
                    return getType(((Operation)expression).rhs);
                }


            default:
                return null;
        }
    }

    public void checkStyleRule(Stylerule parent){
        variableTypes.insert(variableTypes.getSize(), new HashMap<>());
        for (ASTNode child: parent.getChildren()){
            if(child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }
            else if(child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
            else if(child instanceof VariableAssignment){
                insertNewVariable(variableTypes.getSize(), (VariableAssignment) child);
            }
        }
        variableTypes.delete(variableTypes.getSize());
    }

    public void checkDeclaration(Declaration parent){
        for (ASTNode child: parent.getChildren()){
            if (child instanceof PropertyName){
                //Nothing yet
            }

            else if (child instanceof VariableReference) {
                checkIfVariableDefined((VariableReference) child);
            }
            else if (child instanceof Operation){
                checkOperation((Operation) child);
            }
        }
    }

    public void checkIfClause(IfClause parent){
        variableTypes.insert(variableTypes.getSize(), new HashMap<>());
        ASTNode condition = parent.getConditionalExpression();
            if (condition instanceof VariableReference) {
                VariableReference var = (VariableReference) condition;
                if (checkIfVariableDefined(var) && getVariableType(var.name) != ExpressionType.BOOL) {
                    parent.setError("The IF statement condition " + parent.conditionalExpression + " isn't a Boolean");
                }
            }
            else if (!(condition instanceof BoolLiteral)){
                parent.setError("The IF statement condition " + parent.conditionalExpression + " isn't a Boolean");
            }

        for (ASTNode child: parent.getChildren()) {
            if(child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }

            else if(child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }

            else if(child instanceof VariableAssignment){
                insertNewVariable(variableTypes.getSize(), (VariableAssignment) child);
            }

            else if(child instanceof ElseClause){
                checkElseClause((ElseClause) child);
            }
        }
        variableTypes.delete(variableTypes.getSize());
    }

    private void checkElseClause(ElseClause clause) {
        for (ASTNode declaration: clause.body){
            checkDeclaration((Declaration) declaration);
        }

    }

    private void checkOperation(Operation operation){
        if(operation.lhs instanceof ColorLiteral || operation.rhs instanceof ColorLiteral || operation.lhs instanceof BoolLiteral || operation.rhs instanceof BoolLiteral){
            operation.setError("An operation is using a ColorLiteral.");
        }
        else{
            //Andere checks hier of in de operation classes toevoegen.
            if(operation.lhs instanceof Operation){
                checkOperation((Operation) operation.lhs);
            }
            else if(operation.rhs instanceof Operation){
                checkOperation((Operation) operation.rhs);
            }

        }
    }

    private void insertNewVariable(int scopePos, VariableAssignment var) {
        if(var.expression instanceof Operation){
            checkOperation((Operation) (var.expression));
        }
        variableTypes.get(scopePos).put(var.name.name, getType(var.expression));
    }

    private Boolean checkIfVariableDefined(VariableReference variable) {
        for(int i = 1; i <= variableTypes.getSize(); i++){
            if(variableTypes.get(i).get(variable.name) != null){
                return true;
            }
        }
        variable.setError("The variable " + variable.name + " is undefined or out of scope!");
        return false;
    }

    private ExpressionType getVariableType(String name) {
       for(int i = 1; i <= variableTypes.getSize(); i++) {
           if(variableTypes.get(i).containsKey(name)){
               return variableTypes.get(i).get(name);
           }
       }
        return null;
    }

}
