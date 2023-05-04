package nl.han.ica.icss.parser.factories;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.parser.ICSSParser;

public class OperatorsFactory {

    public OperatorsFactory(){}

    public Operation makeNewOperator(ICSSParser.OperationContext ctx){

        switch(ctx.getChild(1).getText()){
            case "*":
                return new MultiplyOperation();

            case "+":
                return new AddOperation();

            case "-":
                return new SubtractOperation();

            default:
                return null;
        }
    }
}
