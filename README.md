# Proyecto para Arquitectura

## Preparando para produccion

### Construyendo como archivo .jar

Para construir el archivo jar y optimizar la aplicacion para produccion, ejecute el comando:

```
./mvnw -Pprod clean package
```

Empaquetara tanto los archivos html, js, y java como resultado de angular en el front, y spring en el back, para ejecutar el archivo jar ejecute:

```
java -jar target/*.jar
```

Entonces navega a [http://localhost:8080](http://localhost:8080) en su navegador.

## Otros
### Docker Compose support

Hay una serie de archivos .yml en [src/main/docker/](src/main/docker/) donde tambien hay herramientas extras para desarrollo como Sonar;

Por ejemplo, para iniciar los servicios requeridos en los contenedores de Docker, ejecute:

```
docker compose -f src/main/docker/services.yml up -d
```

Para detener y eliminar los contenedores, ejecute:

```
docker compose -f src/main/docker/services.yml down
```

[Spring Docker Compose Integration](https://docs.spring.io/spring-boot/reference/features/dev-services.html) esta activado por defecto, para desactivarlo, modifique el application.yml en resources:

```yaml
spring:
  #...
  docker:
    compose:
      enabled: false
```
