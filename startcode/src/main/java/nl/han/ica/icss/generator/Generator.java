package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

public class Generator {

	public String generate(AST ast) {
		StringBuilder sb = new StringBuilder();
		for (ASTNode node: ast.root.getChildren()) {
			if(node instanceof Stylerule){
				Stylerule stylerule = (Stylerule) node;
				for(Selector selector: stylerule.selectors){
					if(selector instanceof ClassSelector){
						sb.append(((ClassSelector) selector).cls);
					}
					else if(selector instanceof IdSelector){
						sb.append(((IdSelector) selector).id);
					}
					else if(selector instanceof TagSelector){
						sb.append(((TagSelector) selector).tag);
					}
				}
				sb.append("{ \n");
				for(ASTNode declarationNode: stylerule.body){
					if(declarationNode instanceof Declaration){
						Declaration declaration = (Declaration) declarationNode;
						String literalValue = getLiteralValue(declaration.expression);
						sb.append("  ").append(declaration.property.name).append(": ").append(literalValue).append(";\n");
					}
				}
				sb.append("} \n");
			}
		}
        return sb.toString();
	}

	private String getLiteralValue(Expression expression){
		if(expression instanceof BoolLiteral){
			return Boolean.toString(((BoolLiteral) expression).value);
		}
		else if(expression instanceof ColorLiteral){
			return ((ColorLiteral) expression).value;
		}
		else if(expression instanceof PercentageLiteral){
			return ((PercentageLiteral) expression).value + "%";
		}
		else if(expression instanceof PixelLiteral){
			return ((PixelLiteral) expression).value + "px";
		}
		else if(expression instanceof ScalarLiteral){
			return ((ScalarLiteral) expression).value + "";
		}

		return "";
	}
}
