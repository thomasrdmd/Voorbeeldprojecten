package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.implementations.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.parser.factories.LiteralsFactory;
import nl.han.ica.icss.parser.factories.OperatorsFactory;
import nl.han.ica.icss.parser.factories.SelectorFactory;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

	//Top level esc
	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		ASTNode styleSheet = new Stylesheet();
		currentContainer.push(styleSheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		ast.setRoot((Stylesheet) currentContainer.pop());
	}

	@Override
	public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		currentContainer.push(new Stylerule());
	}

	@Override
	public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}


	//Selectors
	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		SelectorFactory factory = new SelectorFactory();
		currentContainer.peek().addChild(factory.makeNewSelector(ctx));
		//currentContainer.push(factory.makeNewSelector(ctx));
	}

//	@Override
//	public void exitSelector(ICSSParser.SelectorContext ctx) {
//		ASTNode astNode = currentContainer.pop();
//		currentContainer.peek().addChild(astNode);
//	}


	//Declarations
	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}

	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		currentContainer.push(new PropertyName(ctx.getText()));
	}

	@Override
	public void exitProperty(ICSSParser.PropertyContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}


	//Literals
	@Override
	public void enterLiterals(ICSSParser.LiteralsContext ctx) {
		LiteralsFactory factory = new LiteralsFactory();
		currentContainer.peek().addChild(factory.makeNewLiteral(ctx));
		//currentContainer.push(factory.makeNewLiteral(ctx));
	}

//	@Override
//	public void exitLiterals(ICSSParser.LiteralsContext ctx) {
//		ASTNode astNode = currentContainer.pop();
//		currentContainer.peek().addChild(astNode);
//	}

	@Override
	public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		currentContainer.push(new VariableAssignment());
	}

	@Override
	public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}

	@Override
	public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
		//currentContainer.push(new VariableReference(ctx.getText()));
		currentContainer.peek().addChild(new VariableReference(ctx.getText()));
	}

//	@Override
//	public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
//		ASTNode astNode = currentContainer.pop();
//		currentContainer.peek().addChild(astNode);
//	}


	//Sommen
	@Override
	public void enterOperation(ICSSParser.OperationContext ctx) {
		OperatorsFactory factory = new OperatorsFactory();
		currentContainer.push(factory.makeNewOperator(ctx));
	}

	@Override
	public void exitOperation(ICSSParser.OperationContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}


	//if statements
	@Override
	public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitIfClause(ICSSParser.IfClauseContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}

	@Override
	public void enterElseBody(ICSSParser.ElseBodyContext ctx) {
		currentContainer.push(new ElseClause());
	}

	@Override
	public void exitElseBody(ICSSParser.ElseBodyContext ctx) {
		ASTNode astNode = currentContainer.pop();
		currentContainer.peek().addChild(astNode);
	}

	//Misc
	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		super.enterEveryRule(ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		super.exitEveryRule(ctx);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		super.visitTerminal(node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		super.visitErrorNode(node);
	}
}
