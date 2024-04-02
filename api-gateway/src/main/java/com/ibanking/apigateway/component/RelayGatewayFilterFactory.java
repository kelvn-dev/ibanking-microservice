package com.ibanking.apigateway.component;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class RelayGatewayFilterFactory
    extends AbstractGatewayFilterFactory<RelayGatewayFilterFactory.Config> {

  public RelayGatewayFilterFactory() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) ->
        exchange
            .getPrincipal()
            .cast(JwtAuthenticationToken.class)
            .map(
                jwtAuthenticationToken -> {
                  String userId = jwtAuthenticationToken.getName();
                  addCustomHeader(exchange, "x-user-id", userId);
                  return exchange;
                })
            .defaultIfEmpty(exchange)
            .flatMap(chain::filter);
  }

  private ServerWebExchange addCustomHeader(ServerWebExchange exchange, String key, String value) {
    return exchange.mutate().request(r -> r.headers(headers -> headers.add(key, value))).build();
  }

  public static class Config {
    // ...
  }
}
