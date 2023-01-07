# encheres

-- AVANCEMENT --  
création classe pour créer des objets, choisir une catégorie et un utilisateur.  
création d'une classe pour créer un utilisateur, supprimer toutes les tables  
création d'une classe permettant de recréer toutes les tables, et une de les supprimer  
les mots end, when, sont réservé par sql, on ne peut pas les donner en nom de colonne  
J'ai créé la page de création d'utilisateur, et fait le lien avec la BdD.  
J'ai suivi l'exemple du prof, donc j'ai créé une classe Session où sera stocké "con", ainsi que l'id de l'utilisateur courant (vaut -1 si personne n'est connecté).  
Toujours selon l'exemple du prof, quand on click sur un bouton qui fait changer de page (chaque page est une classe java), en fait je met juste à jour la page principale. D'où l'intérêt de passer "main" dans chaque constructeurs.  
(on fait main.setPrincipal( ... ) pour mettre à jour l'élément principal de la page. Il y a la même chose pour l'entete.)  
