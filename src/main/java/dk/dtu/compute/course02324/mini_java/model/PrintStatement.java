package dk.dtu.compute.course02324.mini_java.model;

import dk.dtu.compute.course02324.mini_java.semantics.ProgramVisitor;

public class PrintStatement implements SimpleStatement {

<<<<<<< HEAD
    public String string;
    public Expression expression;
=======
    public final String string;
    public final Expression expression;
>>>>>>> d3630d0 ( update)

    public PrintStatement(String string, Expression expression){
        this.string=string;
        this.expression=expression;
    }

    @Override
    public void accept(ProgramVisitor visitor) {
        visitor.visit(this);
    }
}