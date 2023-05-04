package nl.han.ica.icss.parser.factories;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.parser.ICSSParser;

public class LiteralsFactory {

    public LiteralsFactory(){}

    public Literal makeNewLiteral(ICSSParser.LiteralsContext ctx){
        switch (ctx.getChild(0).getClass().getSimpleName()){
            case "ColorLiteralContext":
                return new ColorLiteral(ctx.getText());

            case "PixelLiteralContext":
                return new PixelLiteral(ctx.getText());

            case "BoolLiteralContext":
                return new BoolLiteral(ctx.getText());

            case "PercentageLiteralContext":
                return new PercentageLiteral(ctx.getText());

            case "ScalarLiteralContext":
                return new ScalarLiteral(ctx.getText());

            default:
                //Uiteindelijk voor foutafhandeling.
                return null;

        }
    }
}
