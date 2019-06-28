package com.drools.demo.rule.evaluator;

import org.drools.core.base.BaseEvaluator;
import org.drools.core.base.ValueType;
import org.drools.core.base.evaluators.EvaluatorDefinition;
import org.drools.core.base.evaluators.Operator;
import org.drools.core.common.InternalFactHandle;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.rule.VariableRestriction;
import org.drools.core.spi.Evaluator;
import org.drools.core.spi.FieldValue;
import org.drools.core.spi.InternalReadAccessor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

public class BetweenEvaluatorDefinition implements EvaluatorDefinition {
    protected static final String betweenOp = "between";

    public static Operator BETWEEN = Operator.addOperatorToRegistry("between", false);
    public static Operator NOT_BETWEEN = Operator.addOperatorToRegistry("between", true);
    private static String[] SUPPORTED_IDS = {BETWEEN.getOperatorString()};

    private Evaluator[] evaluators;

    @Override
    public String[] getEvaluatorIds() {
        return SUPPORTED_IDS;
    }

    @Override
    public boolean isNegatable() {
        return true;
    }

    @Override
    public Evaluator getEvaluator(ValueType type, String operatorId, boolean isNegated, String parameterText, Target leftTarget, Target rightTarget) {
        return new CustomEvaluator(type, isNegated);
    }

    @Override
    public Evaluator getEvaluator(ValueType type, String operatorId, boolean isNegated, String parameterText) {
        return this.getEvaluator(type, operatorId, isNegated, parameterText, Target.FACT, Target.FACT);
    }

    @Override
    public Evaluator getEvaluator(ValueType type, Operator operator, String parameterText) {
        return this.getEvaluator(type, operator.getOperatorString(), operator.isNegated(), parameterText);
    }

    @Override
    public Evaluator getEvaluator(ValueType type, Operator operator) {
        return this.getEvaluator(type, operator.getOperatorString(), operator.isNegated(), null);
    }

    @Override
    public boolean supportsType(ValueType type) {
        return true;
    }

    @Override
    public Target getTarget() {
        return Target.FACT;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(evaluators);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        evaluators = (Evaluator[]) in.readObject();
    }

    public class CustomEvaluator extends BaseEvaluator {

        CustomEvaluator(ValueType type, boolean isNegated) {
            super(type, isNegated ? NOT_BETWEEN : BETWEEN);
        }

        @Override
        public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor extractor, InternalFactHandle factHandle, FieldValue value) {
            final Object objectValue = extractor.getValue(workingMemory, factHandle);
            return evaluateAll((Date)objectValue, (List<Long>) value.getValue());
        }

        @Override
        public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor leftExtractor, InternalFactHandle left, InternalReadAccessor rightExtractor, InternalFactHandle right) {
            return evaluateAll((Date) left.getObject(), (List<Long>) right.getObject());
        }

        @Override
        public boolean evaluateCachedLeft(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle right) {
            List<Long> valRight = (List<Long>) context.getFieldExtractor().getValue(workingMemory, right.getObject());
            return evaluateAll((Date) ((VariableRestriction.ObjectVariableContextEntry) context).right, valRight);
        }

        @Override
        public boolean evaluateCachedRight(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle left) {
            Date valLeft = (Date) context.declaration.getExtractor().getValue(workingMemory, left);
            return evaluateAll(valLeft, (List<Long>) ((VariableRestriction.ObjectVariableContextEntry) context).right);
        }

        public boolean evaluateAll(Date leftDate, List<Long> rightCollection) {

            if (rightCollection.size() == 2) {
                return leftDate.getTime() > rightCollection.get(0) && leftDate.getTime() < rightCollection.get(1);
            }
            return false;
        }
    }
}
