package nl.han.ica.icss.parser.factories;

import nl.han.ica.icss.ast.Selector;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.parser.ICSSParser;

public class SelectorFactory {

    public SelectorFactory(){}

    public Selector makeNewSelector(ICSSParser.SelectorContext ctx) {
        switch (ctx.getChild(0).getClass().getSimpleName()) {
            case "TagSelectorContext":
                return new TagSelector(ctx.getText());

            case "IdSelectorContext":
                return new IdSelector(ctx.getText());

            case "ClassSelectorContext":
                return new ClassSelector(ctx.getText());

            default:
                //uiteindelijk voor foutafhandeling.
                return null;
        }
    }
}
