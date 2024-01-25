# Introduzione
Questo è il gioco realizzato dal gruppo3.


## Avvio in locale
Per avviare il gioco in locale:
#### Attraverso interfaccia grafica
Cliccare due volte sul file "millennium_problem.jar" ed aprire con "Java".

#### Attraverso terminale
Aprire il terminale e spostarsi nella directory contenente il file 'Gruppo3executable.jar':
```bash
cd directory-file
```
Successivamente, eseguire il gioco con il seguente comando:
```bash
java -jar millennium_problem.jar
```

## Comandi di gioco
#### Menu
- Navigazione nei menu: Utilizzare il cursore del mouse.
#### In partita
- Movimento: Tasti freccia / WASD.
- Interazione: Premere il tasto 'X'.
- Scelta dialogo: Utilizzare i tasti freccia [Su/Giù], premere 'X' per selezionare.


## Test
A causa di un problema con il framework LibGDX, alcuni test mostrano un'eccezione, causata dal fatto che alcune classi di LibGDX tentano di chiamare metodi di rendering, che però sono disabilitati durante il testing (usiamo infatti la classe HeadlessApplication per avviare il gioco senza grafica). Questa eccezione non compromette il testing, che infatti dà esito positivo.

## Storia
Per prima cosa abbiamo lavorato alla composizione della storia alla base del nostro gioco, suddividendola in atti e inserendo alcuni indizi che serviranno per svelare il colpevole nella storia. 

#### Atto Introduttivo
Nella narrazione si impersona uno studente di Informatica dell’Università di Palermo. Nella casa ci saranno varie interazioni tra cui una con un gatto che permetterà di capire le funzionalità del gioco. Infatti si potrà interagire con esso, decidere se accarezzarlo o no e prendere nel frigo del latte da potergli dare da mangiare.
Nel momento in cui si dovesse interagire con la porta si riceverà un messaggio criptato da parte dello stimato professor Rettangolo. Decifrato parzialmente il messaggio, si potrà uscire di casa per andare all’università iniziando l’atto primo. 

#### Primo Atto
Qui si potrà girare il dipartimento ed interagire con vari NPC tra cui il rettore, il primo NPC che ci darà un indizio. Il rettore infatti dirà che lui possiede una mazzo di chiavi che gli permette di accedere ad ogni stanza dell’università. Quando si vuole ci si potrà dirigere verso l’ufficio del professore Rettangolo per chiedere spiegazioni sul messaggio ricevuto. Il professore chiederà per prima cosa se si è riusciti a decifrarlo e verrà posta una scelta: mentire o no. In entrambi i casi il docente, in maniera un pò vaga, accennerà ad un possibile complotto nell’università senza però specificare niente. Nel caso in cui non si mentisse, però, a partire dal secondo atto, si potrà trovare nella sua stanza il secondo indizio: una nota contenente il messaggio decifrato dal professore per intero grazie alla prima interpretazione dello studente. Se invece si dovesse mentire questa nota non spunterà più in quanto il professore non sarà riuscito a decifrarlo.
Nel momento in cui si scende di nuovo al piano terra inizierà il secondo atto.

#### Secondo atto
Presi dalla curiosità, si inizierà ad investigare al fine di trovare le prove che questo complotto sia effettivamente reale. Al solito si troveranno vari NPC ma due hanno particolare importanza: 
il professore Valenza che fornirà nuovamente accesso alla porta (che si è chiusa per sbaglio) per il primo piano e i sotterranei;
il portiere che fornirà la chiave d’accesso alla stanza segreta che si trova al piano sotterraneo;
Se si dovesse scegliere di risalire nello studio del professore ci sarà la possibilità di prendere la nota (se non si è precedentemente mentito).
Entrati nella stanza segreta avrà inizio il terzo atto.

#### Terzo atto
Nella stanza segreta si troverà un pc a cui non si potrà accedere non sapendo la password. Quest'ultima si troverà incisa in una pianta di plastica. Nel frattempo, si potrà interagire con una libreria in cui si troveranno gli appunti del professore che accennano alla risoluzione del problema P=NP e, consapevole che fosse una scoperta pericolosa, ha scelto di non divulgare anche se ha speso molti anni a dimostrarlo. Immettendo la password del pc si vedranno i file della ricerca, capendo la pericolosità di essa.
Uscendo dalla stanza inizierà il quarto atto.

#### Quarto atto
Una volta fuori dalla stanza, ci si troverà si troverà davanti il rettore che, stupito di trovare lo studente, suppone che anch’egli dubiti della credibilità del professor Rettangolo e così lo invita nel suo studio per incolpare il docente. Salendo al primo piano, ed entrando nello studio del rettore, al giocatore verrà chiesto di accusare il professore Rettangolo o il rettore sulla base delle prove raccolte e di chi il giocatore pensa sia colpevole.

#### Atto finale
A questo punto la storia si biforca. Se viene scelto il professor Rettangolo si apre il finale negativo, in cui la polizia lo arresta, ma pochi mesi dopo l’economia mondiale crolla sotto il piano del rettore di utilizzare la ricerca per scopi personali. Se invece viene scelto il rettore come colpevole si apre l’altro finale possibile, quello positivo, in cui una volta mostrate tutte e tre le prove,  il rettore verrà arrestato e ne verrà nominato uno nuovo poco tempo dopo.
 
Così si completa l’atto finale e la storia si conclude con i crediti finali. Ci si troverà in una stanza con i tre NPC: Peppe, Cristina, Andrea che chiederanno se possono utilizzare questa storia per il loro progetto completando la parte della storia secondaria. Se si interagisce infine con questi ultimi ringrazieranno di aver giocato e  avvisano della presenza di un altro finale. Inoltre apparirà il gatto che permetterà di cambiare scelta, tornando al momento prima di dare la colpa ad uno dei due oppure di ricominciare da capo.

Inoltre nel gioco ci sarà anche una storia secondaria: gli NPC Peppe, Cristina ed Andrea li troveremo sempre in aula 4 a svolgere un progetto.
Nel primo atto se si interagisce con essi, si chiederà chi sono e, dopo essersi presentati, affermeranno di star lavorando ad un progetto universitario.
Nel secondo atto si chiederà loro a che punto sono ed essi chiederanno se si ha una storia da usare per il loro progetto, o di ritornare quando se ne avrà una.
Nel quarto atto, esausti dal pensare a come realizzare il progetto, saranno diventati dei fantasmi e riusciranno a parlare utilizzando solo parole sconnesse di fasi di progettazione.

## Soluzione al gioco
Nel gioco sono presenti 3 indizi:

- Il mazzo di chiavi: lo si recepisce parlando con il rettore nel momento in cui, usciti dalla casa iniziale, si va a parlare con il rettore ci dirà che lui possiede un mazzo di chiavi che lo fa accedere in TUTTE le stanze dell'università.

- La nota: La prima volta che parliamo con il professore potremo mentirgli: non dobbiamo mentire. Grazie a questo il professore riuscirà a decifrare l'intero messaggio e lasciare una nota nella sua stanza. Questa nota sarà possibile prenderla in qualsiasi momento dopo essere riscesi al piano terra.

- Gli appunti del professore: nella stanza segreta nella libreria sporgerà un libro che leggendolo, verrà preso dal giocatore.

Grazie a questi tre indizi potremo incolpare il rettore ed ottenere il good ending.