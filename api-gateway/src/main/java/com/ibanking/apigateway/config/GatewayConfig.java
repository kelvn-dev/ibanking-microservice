package com.ibanking.apigateway.config;

import com.ibanking.apigateway.component.RelayGatewayFilterFactory;
import com.ibanking.apigateway.config.properties.PaymentMsPropConfig;
import com.ibanking.apigateway.config.properties.UserMsPropConfig;
import java.util.Arrays;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class GatewayConfig {

  private final RelayGatewayFilterFactory relayGatewayFilterFactory;
  private final UserMsPropConfig userMsPropConfig;
  private final PaymentMsPropConfig paymentMsPropConfig;

  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
    RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
    Arrays.stream(userMsPropConfig.getPaths())
        .forEach(path -> routesBuilder.route(getRoute(path, userMsPropConfig.getName())));
    Arrays.stream(paymentMsPropConfig.getPaths())
        .forEach(path -> routesBuilder.route(getRoute(path, paymentMsPropConfig.getName())));
    return routesBuilder.build();
  }

  private Function<PredicateSpec, Buildable<Route>> getRoute(String path, String uri) {
    return r ->
        r.path(path.concat("/**"))
            .filters(filterSpec -> getGatewayFilterSpec(path, filterSpec))
            .uri("lb://".concat(uri));
  }

  private GatewayFilterSpec getGatewayFilterSpec(String path, GatewayFilterSpec f) {
    return f.rewritePath(path.concat("(?<segment>.*)"), "/api/v1".concat(path).concat("${segment}"))
        .filter(relayGatewayFilterFactory.apply((RelayGatewayFilterFactory.Config) null));
  }
}
