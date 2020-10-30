# concours-photo

Projet pour l'UE programmation web à l'Université de Bordeaux.

## Utilisation de docker-compose
Il faut tout d'abord build le front, avec `ng build --prod`.
Il faut également produire le `.war` pour le back. Dans *intellij*, il faut aller dans `Build/Build Artifact...`, puis aller sur `back:war` dans la popup, puis cliquer sur build.

Ensuite, il suffit de faire `docker-compose up`. (ou `docker-compose up --force-recreate --build` pour être sure de mettre à jour les containers).