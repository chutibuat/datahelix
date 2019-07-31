package com.scottlogic.deg.generator.fieldspecs.relations;

import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.generator.fieldspecs.FieldSpec;
import com.scottlogic.deg.generator.restrictions.DateTimeRestrictions;

import java.time.OffsetDateTime;

public class AfterDateRelation implements FieldSpecRelations {

    private final Field main;

    private final Field other;

    public AfterDateRelation(Field main, Field other) {
        this.main = main;
        this.other = other;
    }

    @Override
    public FieldSpec reduceToRelatedFieldSpec(FieldSpec otherValue) {
        OffsetDateTime min = otherValue.getDateTimeRestrictions().min.getLimit();
        OffsetDateTime adjusted = min.plusNanos(1_000);

        DateTimeRestrictions restrictions = new DateTimeRestrictions();
        restrictions.min = new DateTimeRestrictions.DateTimeLimit(adjusted, false);

        return FieldSpec.Empty.withDateTimeRestrictions(restrictions);
    }

    @Override
    public Field main() {
        return main;
    }

    @Override
    public Field other() {
        return other;
    }

}
