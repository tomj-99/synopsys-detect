package com.blackducksoftware.integration.hub.detect.extraction.bomtool.cocoapods;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.blackducksoftware.integration.hub.detect.extraction.strategy.Strategy;
import com.blackducksoftware.integration.hub.detect.extraction.strategy.StrategyProvider;
import com.blackducksoftware.integration.hub.detect.model.BomToolType;

@Component
public class CocoapodsStrategyProvider extends StrategyProvider {

    public static final String PODFILE_LOCK_FILENAME = "Podfile.lock";

    @SuppressWarnings("rawtypes")
    @Override
    public List<Strategy> createStrategies() {

        final Strategy podlockStrategy = newStrategyBuilder(PodlockContext.class, PodlockExtractor.class)
                .needsBomTool(BomToolType.COCOAPODS).noop()
                .needsCurrentDirectory((context, file) -> context.directory = file)
                .needsFile(PODFILE_LOCK_FILENAME).as((context, file) -> context.podlock = file)
                .build();


        return Arrays.asList(podlockStrategy);

    }

}