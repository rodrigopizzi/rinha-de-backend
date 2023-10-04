# Build stage
FROM ghcr.io/graalvm/native-image:ol8-java17-22.3.3 as graalvm
RUN microdnf -y install wget unzip zip findutils tar
COPY . /app
WORKDIR /app
RUN \
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/.sdkman/bin/sdkman-init.sh"; \
    sdk install gradle; \
    gradle nativeCompile


# Runtime stage
FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /app/build/native/nativeCompile/ /app/rinha
ENTRYPOINT ["/app/rinha/rinha-de-backend"]
