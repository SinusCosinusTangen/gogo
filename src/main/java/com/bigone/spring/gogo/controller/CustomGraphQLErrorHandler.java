package com.bigone.spring.gogo.controller;

import com.bigone.spring.gogo.exception.DuplicateDataException;
import com.bigone.spring.gogo.exception.EmptyInputException;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomGraphQLErrorHandler implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {
        List<GraphQLError> errors = new ArrayList<>();
        if (exception instanceof DuplicateDataException || exception instanceof EmptyInputException) {
            errors.add(GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.ValidationError)
                    .message(exception.getMessage())
                    .path(environment.getExecutionStepInfo().getPath())
                    .build());
            return Mono.just(errors);
        } else if (exception instanceof NullPointerException) {
            errors.add(GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.NullValueInNonNullableField)
                    .message(exception.getMessage())
                    .path(environment.getExecutionStepInfo().getPath())
                    .build());
            return Mono.just(errors);
        }
        return null;
    }
}
