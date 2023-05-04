package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;

public class SubtractOperation extends Operation {

    @Override
    public String getNodeLabel() {
        return "Subtract";
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
