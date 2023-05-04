grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
stylesheet: (variableAssignment)* (styleRule)+ EOF;

styleRule: selector OPEN_BRACE (declaration | ifClause | variableAssignment)+ CLOSE_BRACE;

//--- Selectors---
selector: (tagSelector | idSelector | classSelector);
tagSelector: LOWER_IDENT;
idSelector: ID_IDENT;
classSelector: CLASS_IDENT;

//--- Declarations: ---
declaration: property COLON (literals | variableReference | operation) SEMICOLON;

property: LOWER_IDENT;


//---Literals---
literals: (colorLiteral | pixelLiteral | boolLiteral | percentageLiteral | scalarLiteral);
colorLiteral: COLOR;
pixelLiteral: PIXELSIZE;
boolLiteral: (TRUE | FALSE);
percentageLiteral: PERCENTAGE;
scalarLiteral: SCALAR;


//-- Assignments ---
variableAssignment: variableReference ASSIGNMENT_OPERATOR (literals | operation | variableReference) SEMICOLON;

variableReference: CAPITAL_IDENT;

operation: (literals | variableReference) operator (operation | literals | variableReference);
operator: (MUL | PLUS | MIN);


//If clause
ifClause: IF BOX_BRACKET_OPEN condition BOX_BRACKET_CLOSE OPEN_BRACE (ifBody)+ CLOSE_BRACE (ELSE OPEN_BRACE (elseBody)+ CLOSE_BRACE)*;
condition: (variableReference | literals);
ifBody: (declaration | ifClause | variableAssignment);
elseBody: declaration;
