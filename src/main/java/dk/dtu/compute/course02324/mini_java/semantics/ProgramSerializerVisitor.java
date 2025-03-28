package dk.dtu.compute.course02324.mini_java.semantics;

import dk.dtu.compute.course02324.mini_java.model.*;

import java.util.HashMap;
import java.util.Map;

public class ProgramSerializerVisitor extends ProgramVisitor  {

    private String result = "";

    private Map<Statement, String> statementRepresentations = new HashMap<>();

    private Map<Expression, String> expressionRepresentations = new HashMap<>();

    @Override
    public void visit(Sequence sequence) {
        result = "";
        for (Statement statement: sequence.statements) {
            result += statementRepresentations.get(statement) + System.lineSeparator();
        }
        statementRepresentations.put(sequence, result);
    }

    @Override
    public void visit(Declaration declaration) {
        result = declaration.type.getName() + " " + declaration.variable.name;
        if (declaration.expression == null) {
            result += ";";
        } else {
            result += " = " + expressionRepresentations.get(declaration.expression) + ";";
        }
        statementRepresentations.put(declaration, result);
    }

    @Override
    public void visit(Assignment assignment) {
        result = assignment.variable.name  + " = " + expressionRepresentations.get(assignment.expression);
        statementRepresentations.put(assignment, result);
        expressionRepresentations.put(assignment, result);
    }

    @Override
    public void visit(Literal literal) {
        result = "";
        if (literal instanceof IntLiteral) {
            result += ((IntLiteral) literal).literal;
        } else if (literal instanceof FloatLiteral) {
            result += ((FloatLiteral) literal).literal + "f";
        } else {
            assert false;
        }
        expressionRepresentations.put(literal, result);
    }

    @Override
    public void visit(Var var) {
        result = var.name;
        expressionRepresentations.put(var, result);
    }

    public void visit(PrintStatement printStatement){
<<<<<<< HEAD
        result = printStatement.string + ": " + printStatement.expression;
        expessionRepresentations.put(printStatement.expression, printStatement.string);
=======
        //Gemmer et result af vores string og expression
       printStatement.expression.accept(this);
       result = "println(\"" + printStatement.string+ "\","
               + expressionRepresentations.get(printStatement.expression)+");";
               statementRepresentations.put(printStatement,result);
>>>>>>> d3630d0 ( update)
    }

    @Override
    public void visit(OperatorExpression operatorExpression) {
        if (operatorExpression.operands.size() == 0) {
            result = operatorExpression.operator.getName() +"()";
        } else if (operatorExpression.operands.size() == 1) {
            result = operatorExpression.operator.getName() + " " +
                    expressionRepresentations.get(operatorExpression.operands.getFirst());
        } else if (operatorExpression.operands.size() == 2) {
            result = operandToString(operatorExpression.operator, operatorExpression.operands.getFirst(),0) + " " +
                    operatorExpression.operator.getName() + " " +
                    operandToString(operatorExpression.operator, operatorExpression.operands.getLast(), 1);
        } else {
            result = operatorExpression.operator.getName() + "(";
            boolean first = true;
            for (Expression operand : operatorExpression.operands) {
                if (!first) {
                    result += ", ";
                } else {
                    first = false;
                }
                result += expressionRepresentations.get(operand);
            }
            result += ")";
        }
        expressionRepresentations.put(operatorExpression, result);
    }

    private String operandToString(Operator operator, Expression expression, int number) {
        String result = expressionRepresentations.get(expression);
        if (expression instanceof OperatorExpression) {
            OperatorExpression operatorExpression = (OperatorExpression) expression;
            if (operatorExpression.operator.precedence > operator.precedence ||
                    (operatorExpression.operator.precedence == operator.precedence &&
                            ((operator.associativity == Associativity.LtR && number == 0) ||
                                    (operator.associativity == Associativity.RtL && number == 1)))) {
                return result;
            } else {
                return "( " + result + " )";
            }
        } else if (expression instanceof Assignment) {
            return "( " + result + " )";
        }

        return result;
    }

    public String result() {
        return result;
    }
}
