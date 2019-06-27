package com.drools.demo.rule;

import org.drools.core.base.BaseEvaluator;
import org.drools.core.common.InternalFactHandle;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.rule.VariableRestriction;
import org.drools.core.spi.FieldValue;
import org.drools.core.spi.InternalReadAccessor;

public class CustomEvaluator extends BaseEvaluator {
    @Override
    public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor extractor, InternalFactHandle factHandle, FieldValue value) {
        return false;
    }

    @Override
    public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor leftExtractor, InternalFactHandle left, InternalReadAccessor rightExtractor, InternalFactHandle right) {
        return false;
    }

    @Override
    public boolean evaluateCachedLeft(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle right) {
        return false;
    }

    @Override
    public boolean evaluateCachedRight(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle left) {
        return false;
    }
}
