package com.vk.dwzkf.magic_factory.util;

public final class ConditionUtil {
    private boolean conditionValue;

    private ConditionUtil(boolean conditionValue) {
        this.conditionValue = conditionValue;
    }

    private ConditionUtil setConditionValue(boolean conditionValue) {
        this.conditionValue = conditionValue;
        return this;
    }

    private ConditionUtil(ConditionUtil condition) {
        this.conditionValue = condition.get();
    }

    public static ConditionUtil condition(boolean conditionValue) {
        return new ConditionUtil(conditionValue);
    }

    public static ConditionUtil condition(ConditionUtil condition) {
        checkNotNull(condition);
        return new ConditionUtil(condition);
    }

    public ConditionUtil and(boolean secondConditionValue) {
        return setConditionValue(conditionValue & secondConditionValue);
    }

    public ConditionUtil or(boolean secondConditionValue) {
        return setConditionValue(conditionValue || secondConditionValue);
    }

    public ConditionUtil and(ConditionUtil secondCondition) {
        checkNotNull(secondCondition);
        return setConditionValue(conditionValue && secondCondition.get());
    }

    public ConditionUtil or(ConditionUtil secondCondition) {
        checkNotNull(secondCondition);
        return setConditionValue(conditionValue || secondCondition.get());
    }

    private static void checkNotNull(ConditionUtil conditionUtil) {
        if (conditionUtil == null) {
            throw new IllegalArgumentException(ConditionUtil.class.getName() + " exception: Method arg cannot be null.");
        }
    }

    public boolean get() {
        return conditionValue;
    }
}
