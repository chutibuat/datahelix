package com.scottlogic.deg.generator.generation.combination_strategies;

import com.scottlogic.deg.generator.Field;
import com.scottlogic.deg.generator.generation.databags.DataBag;
import gherkin.lexer.Da;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Assert;

import java.sql.DatabaseMetaData;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class CombinationStrategyTester {
    private ICombinationStrategy strategy;
    private Stream<Stream<DataBag>> dataBags;

    CombinationStrategyTester(ICombinationStrategy combinationStrategy) {
        strategy = combinationStrategy;
    }

    @SafeVarargs
    final void given(Stream<DataBag>... bagSequences) {
        dataBags = Stream.of(bagSequences);
    }

    void expect(Stream<DataBag> bagSequence) {
        DataBag[] results = strategy.permute(dataBags).toArray(DataBag[]::new);
        DataBag[] bagArray = bagSequence.toArray(DataBag[]::new);

        Assert.assertThat(results, IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(bagArray));
    }

    void expectEmpty() {
        Stream<DataBag> results = strategy.permute(dataBags);

        Assert.assertFalse(results.iterator().hasNext());
    }

    static DataBag bag(String... fieldNames) {
        DataBag.DataBagBuilder builder = DataBag.startBuilding();

        for (String fieldName : fieldNames) {
            builder.set(new Field(fieldName), "whatever");
        }

        return builder.build();
    }
}