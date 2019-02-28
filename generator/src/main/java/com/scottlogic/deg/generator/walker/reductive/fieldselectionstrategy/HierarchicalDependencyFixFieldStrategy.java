package com.scottlogic.deg.generator.walker.reductive.fieldselectionstrategy;

import com.google.inject.Inject;
import com.scottlogic.deg.generator.Field;
import com.scottlogic.deg.generator.guice.ProfileProvider;
import com.scottlogic.deg.generator.analysis.FieldDependencyAnalyser;
import com.scottlogic.deg.generator.analysis.FieldDependencyAnalysisResult;

import java.util.Comparator;

public final class HierarchicalDependencyFixFieldStrategy extends ProfileBasedFixFieldStrategy {

    private final SetBasedFixFieldStrategy setBasedFixFieldStrategy;
    private final FieldDependencyAnalyser analyser;

    @Inject
    public HierarchicalDependencyFixFieldStrategy(ProfileProvider profileProvider, FieldDependencyAnalyser analyser) {
        super(profileProvider);
        this.setBasedFixFieldStrategy = new SetBasedFixFieldStrategy(profileProvider);
        this.analyser = analyser;
    }

    Comparator<Field> getFieldOrderingStrategy() {
        FieldDependencyAnalysisResult result = this.analyser.analyse(profileProvider.get());
        Comparator<Field> firstComparison = Comparator.comparingInt(field -> result.getDependenciesOf(field).size());
        Comparator<Field> secondComparison = Comparator.comparingInt((Field field) -> result.getDependentsOf(field).size()).reversed();
        return firstComparison.thenComparing(secondComparison)
            .thenComparing(setBasedFixFieldStrategy.getFieldOrderingStrategy())
            .thenComparing(field -> field.name);
    }

}