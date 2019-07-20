# Burger's terminal

**Le projet**

Nous avons une application permettant de reproduire les bornes de commande que l'on retouve dans les fast-food.

**L'équipe**

Notre groupe est composé des personnes suivantes :

- Jean DEYEHE
- Mickael MOREIRA
- Guillaume TAKO
- Christopher LUKOMBO

Prérequis :

Mettre la variable dans VM arguments au niveau du build MAVEN dans eclipse ou Intellij

-DCONF_DIR=/home/christopher/Documents

Ajouter une copie du fichier de configuration "burgersterminal.properties" correspondant au chemin indiqué dans la variable -DCONF_DIR. Le fichier "burgersterminal.properties" permettra d'externaliser les variables de configuration.

Aussi, mettre également la variable dans VM arguments au niveau du build MAVEN dans eclipse ou Intellij
-Dlogging.config="file:C:\Users\Christopher LUKOMBO\ProgramDev\conf\burgersterminal\logback-spring.xml"

Puis, ajouter une copie du fichier logback-spring.xml dans le chemin spécifié.
Editez ensuite le fichier logback-spring.xml, remplacer la valeur de la property value par le chemin où vous voulez stocker les logs.
<property name="LOGS" value="C:\\Users\\Christopher LUKOMBO\\ProgramDev\\logs\\burgersterminal" />

Créer la base de données MySQL "burgersterminal"

Pour lancer la partie Angular, se placer dans le dossier front et lancer la commande ng serve --open


Swagger : http://localhost:8080/swagger-ui.html#
SEMAPHORE : https://semaphoreci.com/christopherlukombo/burger-s-terminal
SONARCLOUD : https://sonarcloud.io/dashboard?id=ChristopherLukombo_burger-s-terminal

HEALTHCHECK : http://localhost:9000/actuator/health/

Pour activer le profile prod utiliser la variable de conf suivante : 

-Dspring.profiles.active=prod
