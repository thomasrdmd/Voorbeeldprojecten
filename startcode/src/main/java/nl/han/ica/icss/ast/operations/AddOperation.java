package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.PixelLiteral;

public class AddOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Add";
    }

    //See Operation.java

//    @Override
//    public void checkOperation() {
//            //TODO: Verdere checks hieraan toevoegen.
//            if(lhs instanceof Operation){
//                ((Operation) lhs).checkOperation();
//            }
//            else if(rhs instanceof Operation){
//                ((Operation) rhs).checkOperation();
//            }
//    }
}
