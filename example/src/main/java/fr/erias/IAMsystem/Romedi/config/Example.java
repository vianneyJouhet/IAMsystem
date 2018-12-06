package fr.erias.IAMsystem.Romedi.config;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.apache.lucene.queryparser.classic.ParseException;

import com.pengyifan.brat.BratDocument;

import fr.erias.IAMsystem.brat.BratDocumentWriter;
import fr.erias.IAMsystem.brat.CTbrat;
import fr.erias.IAMsystem.ct.CTcode;
import fr.erias.IAMsystem.exceptions.InvalidArraysLength;
import fr.erias.IAMsystem.exceptions.InvalidCSV;
import fr.erias.IAMsystem.exceptions.ProcessSentenceException;
import fr.erias.IAMsystem.exceptions.UnfoundTokenInSentence;


/**
 * 1) Execute NormalizedLabelsRomedi to normalize the labels of the terminology
 * 2) Execute IndexBigramLuceneRomedi to create a Lucene Index to detect typos with Levenshtein distance
 * 3) Execute this example
 * 
 * @author Cossin Sebastien
 *
 */
public class Example {

	public static void drugDetectionExample(String sentence)  throws IOException, UnfoundTokenInSentence, ParseException, InvalidCSV, ProcessSentenceException {
		System.out.println("------------------ DrugDetection Example --------------------------");
		DetectDrug detectDrug = new DetectDrug();
		Set<CTcode> results = detectDrug.getCTcodes(sentence);
		for (CTcode codes : results) {
			System.out.println("--------- New Entry detected --------- : \t " + codes.getCandidateTermString());
			System.out.println("\t start position : " + codes.getStartPosition());
			System.out.println("\t end position : " + codes.getEndPosition());
			System.out.println("\t code : " + codes.getCode());
		}
		System.out.println("------------------ End --------------------------");
	}
	
	public static void bratOutputExample(String sentence)  throws IOException, UnfoundTokenInSentence, ParseException, InvalidCSV, ProcessSentenceException {
		System.out.println("------------------ Brat Output Example --------------------------");
		DetectDrug detectDrug = new DetectDrug();
		Set<CTcode> results = detectDrug.getCTcodes(sentence);
		String bratType = "drug";
		
		// output to the console
		BratDocumentWriter bratDocumentWriter = new BratDocumentWriter(new PrintWriter(System.out));
		BratDocument doc = new BratDocument();
		for (CTcode codes : results) {
			CTbrat ctbrat = new CTbrat(codes, bratType);
			doc.addAnnotation(ctbrat.getBratEntity());
		}
		bratDocumentWriter.write(doc);
		bratDocumentWriter.close();
		System.out.println("------------------ End --------------------------");
	}
	
	// example
	public static void main(String[] args) throws IOException, UnfoundTokenInSentence, ParseException, ProcessSentenceException, InvalidArraysLength, InvalidCSV {
		// ac and KDG are detected with abbreviations
		// acetylsalicilique and kardegik contain typos
		
		String sentence = "	\n" + 
				"Syndrome de Rett\n" + 
				"Sauter à la navigation\n" + 
				"Sauter à la recherche\n" + 
				"Si ce bandeau n'est plus pertinent, retirez-le. Cliquez pour voir d'autres modèles.\n" + 
				"Certaines informations figurant dans cet article ou cette section devraient être mieux reliées aux sources mentionnées dans les sections « Bibliographie », « Sources » ou « Liens externes » (septembre 2012).\n" + 
				"\n" + 
				"Améliorez sa vérifiabilité en les associant par des références à l'aide d'appels de notes.\n" + 
				"Syndrome de Rett\n" + 
				"Description de l'image RettScoliosis.png.\n" + 
				"Données clés Spécialité 	Pédiatrie, psychiatrie et neurologie\n" + 
				"Classification et ressources externes CIM-10 	F84.2\n" + 
				"CIM-9 	330.8\n" + 
				"OMIM 	312750\n" + 
				"DiseasesDB 	29908\n" + 
				"MedlinePlus 	001536\n" + 
				"MeSH 	D015518\n" + 
				"MeSH 	C10.574.500.775\n" + 
				"GeneReviews 	[2]\n" + 
				"\n" + 
				"Wikipédia ne donne pas de conseils médicaux Mise en garde médicale\n" + 
				"\n" + 
				"modifier - modifier le code - voir wikidata Consultez la documentation du modèle\n" + 
				"\n" + 
				"Le syndrome de Rett est une maladie génétique rare se développant chez le très jeune enfant, principalement la fille, et provoquant un handicap mental et des atteintes motrices sévères.\n" + 
				"\n" + 
				"Il s'agit de la première cause de polyhandicap d'origine génétique en France chez les filles et on estime qu'il touche environ 50 nouvelles personnes par an en France.\n" + 
				"Sommaire\n" + 
				"\n" + 
				"    1 Génétique\n" + 
				"    2 Description\n" + 
				"    3 Prévalence\n" + 
				"    4 Critères de diagnostic\n" + 
				"        4.1 Clinique\n" + 
				"        4.2 Diagnostic différentiel\n" + 
				"    5 Évolution\n" + 
				"    6 Pronostic\n" + 
				"    7 Traitement\n" + 
				"    8 Recherche\n" + 
				"        8.1 Médicaments\n" + 
				"        8.2 Facteurs de croissance\n" + 
				"        8.3 Neuro-immunologie\n" + 
				"        8.4 Stimulation cérébrale profonde\n" + 
				"        8.5 Thérapie génique\n" + 
				"        8.6 Financement de programmes de recherche\n" + 
				"        8.7 Congrès internationaux\n" + 
				"    9 Soutien caritatif\n" + 
				"    10 Histoire\n" + 
				"    11 Notes et références\n" + 
				"    12 Voir aussi\n" + 
				"        12.1 Articles connexes\n" + 
				"        12.2 Liens externes\n" + 
				"\n" + 
				"Génétique\n" + 
				"\n" + 
				"Le syndrome de Rett typique est associé dans près de 95 à 97 % des cas à une mutation dans le gène MECP2 (Méthyl-CpG-binding protein 2), localisé sur le bras long (q) du chromosome X, dans la région Xq281. Il s'agit d'une encéphalopathie neurodéveloppementale très particulière, touchant essentiellement les filles, et étant dans plus de 99 % une mutation de novo.\n" + 
				"\n" + 
				"Dans sa forme typique, elle est caractérisée par une décélération globale du développement psychomoteur, puis d’une perte des acquisitions cognitives et motrices, survenant après une période de développement normal.\n" + 
				"\n" + 
				"Dans les formes atypiques, des mutations dans le gène CDKL5 (Cyclin dependent kinaselike 5) et dans le gène FOXG1 (Forkhead box G1) ont été retrouvées chez des enfants avec épilepsie précoce ou porteurs de formes congénitales. Certains patients diagnostiqués en raison de leur phénotype, mais négatifs pour les mutations dans les gènes MECP2, CDKL5 ou FOXG1, ont été soumis au séquençage complet de l'exome, ce qui a permis d'identifier plusieurs dizaines de nouveaux gènes2.\n" + 
				"Carte du chromosome montrant le locus du gène à une de ses extrémités\n" + 
				"Le locus en rouge localise le gène responsable à l'extrémité inférieure de la figure.\n" + 
				"Description\n" + 
				"\n" + 
				"Le syndrome de Rett se manifeste après une période d'évolution normale durant 6 à 18 mois.\n" + 
				"\n" + 
				"Une période de régression apparaît alors avec3 :\n" + 
				"\n" + 
				"    perte partielle ou complète de l'utilisation volontaire des mains ;\n" + 
				"    mise en place de stéréotypies manuelles caractéristiques (pressions, torsion, applaudissement, frottement, lavage des mains) ;\n" + 
				"    perte partielle ou complète du langage oral ;\n" + 
				"    perte ou défaut d'acquisition de la marche ou marche robotisée ;\n" + 
				"    dysfonctionnement respiratoire avec des épisodes d'apnée pendant l'éveil, d'hyperventilation intermittente, des épisodes de blocage de la respiration, d'expulsion de l'air ou de salive ;\n" + 
				"    troubles de la communication et retrait social dès la petite enfance ;\n" + 
				"    détérioration du comportement, manifestations autistiques et rires ou excès de cris ou de pleurs inappropriés ;\n" + 
				"    apparence de retard mental sévère, due à des tests de QI non adaptés4,5,6 ;\n" + 
				"    crises d'épilepsie ;\n" + 
				"    déformations orthopédiques (scoliose, cyphose) ;\n" + 
				"    communication par le regard intense – « eye pointing » ;\n" + 
				"    ostéoporose ;\n" + 
				"    troubles du tonus ;\n" + 
				"    troubles du sommeil ;\n" + 
				"    retard de croissance ;\n" + 
				"    troubles vasomoteurs ;\n" + 
				"    mains et pieds petits, hypotrophiques ;\n" + 
				"    bruxisme pendant l'éveil.\n" + 
				"\n" + 
				"Ni les patientes atteintes du syndrome de Rett, ni les souris modèle de la pathologie ne présentent de signes de dégénérescence cellulaire. Ceci suggère que la maladie est provoquée par un dysfonctionnement des réseaux neuronaux plutôt que par une perte de neurones. Grace à des travaux menés chez des souris modèle, l’équipe d’Adrian Bird, en Ecosse, a montré en 20077 que ce syndrome était réversible si la protéine Mecp2 était ré-exprimée, même à un stade avancé de la maladie8.\n" + 
				"Prévalence\n" + 
				"\n" + 
				"Le syndrome de Rett représente l'une des principales causes de retard mental chez la femme dans le monde, sa prévalence est selon le CNRS de 1⁄10 000 enfants9. Il s'agit de la première cause de polyhandicap d'origine génétique en France chez les filles 10[réf. à confirmer].\n" + 
				"Critères de diagnostic\n" + 
				"Clinique\n" + 
				"\n" + 
				"Les critères de diagnostic du syndrome de Rett, retenus en 1988 et toujours d'actualité, sont11 :\n" + 
				"\n" + 
				"    un développement apparemment normal jusqu’à l’âge de 6 à 8 mois ;\n" + 
				"    un périmètre crânien normal à la naissance, ralentissement de la croissance du crâne entre 3 mois et 4 ans ;\n" + 
				"    l'absence d’un développement normal du langage ;\n" + 
				"    la présence de mouvements répétitifs des mains (lavage de mains, torsions, etc.) ;\n" + 
				"    une apraxie/ataxie du tronc et perte de l’utilisation volontaire des mains entre six et trente mois ;\n" + 
				"    une démarche instable ou mal assurée (lorsque l’enfant marche).\n" + 
				"\n" + 
				"Les critères d'exclusion qui, s'ils sont présents, permettent d'exclure la maladie de Rett sont :\n" + 
				"\n" + 
				"    une rétinopathie ou atrophie optique ;\n" + 
				"    un retard de développement intra-utérin ;\n" + 
				"    une microcéphalie congénitale ;\n" + 
				"    des signes d'une maladie métabolique identifiable ;\n" + 
				"    des signes d'une maladie neurologique identifiable et progressive ;\n" + 
				"    des lésions cérébrales acquises en période périnatale (à la suite d'une infection ou d'un traumatisme crânien) ;\n" + 
				"    une viscéromégalie ou des signes d'une maladie de surcharge.\n" + 
				"\n" + 
				"D'autres symptômes peuvent être présents et aider au diagnostic : épilepsie, trouble du sommeil, anomalies à l'électro-encéphalogramme, spasticité musculaire avec atrophie musculaire et dystonie, scoliose, hypotrophie des pieds, raccourcissement du 4e métacarpien et/ou métatarsien à la radiographie, retard de croissance, grincements de dents, trouble de la déglutition et de la mastication, mauvaise circulation des membres inférieurs.\n" + 
				"\n" + 
				"Les troubles respiratoires sont fréquents et peuvent être de plusieurs types, nécessitant un prise en charge différente12 : hyperventilation avec un risque d'alcalose respiratoire ou, au contraire, hypoventilation avec acidose respiratoire, pouvant être accompagnée d’apnées.\n" + 
				"\n" + 
				"Les autres symptômes comportementaux sont l'irritabilité, l'agitation avec hurlements et peur des agressions, des pleurs inconsolables, un regard fuyant (évitant de croiser le regard des autres). Il peut exister une absence d’implication émotionnelle ou sociale, avec absence de sujets d’intérêt en général et un refus marqué d'utiliser les comportements sociaux non verbaux, ce qui peut évoquer un autisme.\n" + 
				"Diagnostic différentiel\n" + 
				"Si ce bandeau n'est plus pertinent, retirez-le. Cliquez pour voir d'autres modèles.\n" + 
				"Cet article contient une ou plusieurs listes (février 2012).\n" + 
				"\n" + 
				"Ces listes gagneraient à être introduites par une partie rédigée et sourcée, de façon à bien resituer les différents items.\n" + 
				"\n" + 
				"    Autisme\n" + 
				"    Syndrome d'Angelman\n" + 
				"    Syndrome d'Haltia-Santavuori\n" + 
				"\n" + 
				"Évolution\n" + 
				"\n" + 
				"Après une période silencieuse, pendant laquelle le développement est normal, le syndrome de Rett évolue en 4 stades13 :\n" + 
				"\n" + 
				"    Le stade I de stagnation précoce, entre 6 et 18 mois, avec retard des acquisitions psychomotrices sans réelle régression ;\n" + 
				"    Le stade II de régression neurologique rapide, entre 1 et 4 ans, avec régression des acquisitions motrices et mentales, perte de l'usage des mains et apparition de stéréotypies manuelles typiques ;\n" + 
				"    Le stade III de stabilisation apparente, dite « phase de réveil », entre 2 et 10 ans, concerne essentiellement les filles ayant acquis la marche. Il y a régression motrice mais amélioration des capacités de communication avec contact visuel et diminution des caractères autistiques. La majorité des patientes gardent une « pseudo marche » jusqu'à l'âge adulte ;\n" + 
				"    Le stade IV de détérioration motrice tardive, après 10 ans. Avec perte de la marche si elle avait été acquise, trouble du tonus et déformation squelettique. Malgré cette détérioration motrice, la socialisation et le contact visuel sont conservés pendant toute la vie adulte.\n" + 
				"\n" + 
				"Pronostic\n" + 
				"\n" + 
				"Le syndrome de Rett n'est identifié que depuis 1983. Encore aujourd'hui, de nombreuses personnes adultes atteintes du syndrome de Rett ne sont pas ou sont mal diagnostiquées. Dans ces conditions et s'agissant d'une maladie rare, il est difficile d'établir une analyse statistique fiable.\n" + 
				"\n" + 
				"La survie est relativement bonne et les patientes dépassent habituellement l'âge de 10 ans et 70 % d'entre elles atteignent les 35 ans. Cette survie prolongée implique de prévoir une prise en charge multi-disciplinaire sur le long terme.\n" + 
				"Il existe une fréquence élevée de mort subite inexpliquée à l'âge adulte. On connait14 cependant le cas d'une Norvégienne qui a atteint l'âge de 60 ans (mais aucun test génétique n'a permis de confirmer le diagnostic) et d'une Danoise, née en 1923, chez qui on a retrouvé une anomalie du gène lorsqu'elle a eu 66 ans. Elle est décédée à l'âge de 79 ans, des suites d'une péritonite15.\n" + 
				"Traitement\n" + 
				"\n" + 
				"Pour l'humain, il n'existe actuellement pas de traitement spécifique à visée curative pour les enfants atteints. Les traitements symptomatiques doivent être entrepris dès que nécessaire avec par exemple un traitement anti-épileptique si nécessaire, une prise en charge médicale en cas de scoliose sévère, une alimentation adaptée et riche en apport calcique.\n" + 
				"\n" + 
				"La prise en charge éducative est particulièrement importante et doit être adaptée au cas par cas. Cette prise en charge doit être entreprise le plus tôt possible pour avoir le plus de chance de progression. Pour essayer d'obtenir la plus grande autonomie possible de la part de la patiente, il faut travailler et entretenir sa motricité et sa coordination avec un kinésithérapeute et un psychomotricien ; proposer des activités variées, sportives et ludiques, pour faciliter son intégration à l'environnement ; encourager et développer ses facultés de communication, d’échange et de relationnel.\n" + 
				"\n" + 
				"En matière de communication, l'exemple de Karly Wahlin (1985-2012) est un espoir pour les malades et leurs familles. Atteinte du syndrome de Rett, la jeune Américaine avait appris à utiliser un clavier d'ordinateur pour communiquer à l'âge de 10 ans. Elle écrivait des poèmes, composait de la musique, et tenait même un blog16.\n" + 
				"\n" + 
				"En France, les parents peuvent recourir à l’Association française du syndrome de Rett17.\n" + 
				"Recherche\n" + 
				"Médicaments\n" + 
				"\n" + 
				"L'efficacité d'un traitement in vivo par la cystéamine a été démontrée 18. Cette molécule agit sur les mécanismes du transport axonal. Les souris traitées ont une durée de vie augmentée et une fonction motrice améliorée par rapport aux animaux non traités. La cystéamine est autorisée aux États-Unis dans le traitement d'une autre maladie rare de l'enfant, la cystinose.\n" + 
				"\n" + 
				"Il a été démontré que les récepteurs d'une substance chimique du cerveau appelée glutamate, en particulier le type NMDA, sont plus nombreux dans le cerveau des jeunes patientes touchées par le syndrome de Rett. Cette substance peut provoquer des interférences entre les cellules nerveuses du cerveau, contribuant en partie aux convulsions, troubles du comportement et difficultés d'apprentissage. Le dextrométhorphane est en cours de test pour contrer ou bloquer les effets de cette substance chimique du cerveau. Le dextrométhorphane est utilisé comme médicament contre la toux19.\n" + 
				"\n" + 
				"La kétamine a également été testée chez un modèle animal avec des résultats prometteurs20. Il est rapporté que l'utilisation de kétamine chez des souris atteintes du syndrome de Rett peut inverser les anomalies de l'activité cérébrale et améliorer la fonction neurologique de ces animaux21. La kétamine peut avoir de puissants effets anesthésiques ; des travaux supplémentaires sont nécessaires pour établir l'innocuité et la faisabilité d'un traitement chez les patients atteints du syndrome de Rett. Un essai clinique est programmé22.\n" + 
				"\n" + 
				"L'étude des effets de la désipramine a été étudiée en France23. Lors du 4e congrès européen sur le syndrome de Rett en 2015, il a cependant été indiqué24 que les tests n'ont pas permis de mettre en évidence d'amélioration de la fonction respiratoire par rapport à un placebo.\n" + 
				"\n" + 
				"Dans la famille des monoamines, la société Newron Pharmaceuticals a lancé25 en mai 2016 un essai thérapeutique sur le Sarizotan, qui permettrait d'améliorer la fonction respiratoire et de supprimer les apnées. L'essai clinique est organisé aux USA, en Inde, en Italie et au Royaume-Uni26. Les résultats sont attendus pour la fin de l'année 2017.\n" + 
				"Facteurs de croissance\n" + 
				"\n" + 
				"Les agents qui favorisent le développement du cerveau et la fonction synaptique, comme l’insuline-like growth factor 1 (IGF1), sont des molécules intéressantes27 avec des tests en cours chez des patients28.\n" + 
				"\n" + 
				"Dans cette famille de molécules, le NNZ-2566 ou trofinetide est en cours d'essai clinique29. À la fin de l'année 2014, la société Neuren Pharmaceuticals a annoncé des résultats encourageants30. Un nouvel essai de la molécule a été réalisé en 2016-2017 auprès d'une population pédiatrique ; selon la société Neuren, les résultats démontrent un bénéfice clinique significatif31, et la réalisation d'un essai de phase 3 en 2018 sera discutée avec la Food and Drug Administration américaine.\n" + 
				"Neuro-immunologie\n" + 
				"\n" + 
				"L'utilisation d'une greffe de moelle osseuse pour remplacer les cellules défectueuses du système immunitaire dans des souris modèles du syndrome de Rett a stoppé de nombreux symptômes graves de la maladie (respiration et mouvement anormaux) et considérablement étendu la durée de vie des souris Rett32. On connait le cas d'une fille atteinte du syndrome de Rett, traitée pour une leucémie, qui a vu ses capacités de communication accrues après une greffe de moelle osseuse, et qui a pu converser avec sa mère pour la première fois de sa vie33.\n" + 
				"Stimulation cérébrale profonde\n" + 
				"\n" + 
				"L'utilisation de cette technique invasive, réservée à certains troubles (maladie de Parkinson), est actuellement à l'étude au stade pré-clinique34.\n" + 
				"Thérapie génique\n" + 
				"\n" + 
				"L'activation du gène MeCP2 par l'équipe d'Adrian Bird a permis d'améliorer l'atteinte neurologique chez un modèle animal35.\n" + 
				"\n" + 
				"Ces travaux démontrent que la restauration du gène MECP2, même à des stades adultes, corrige plusieurs aspects de la pathologie ; ils suggèrent que la maladie peut être traitée de façon inhérente. Cela permet d'envisager plusieurs approches thérapeutiques ciblant la maladie au niveau du gène, parmi lesquelles :\n" + 
				"\n" + 
				"    la thérapie génique, avec des recherches en cours aux USA et en Europe36 ;\n" + 
				"    l'activation du gène MECP2 sur le chromosome X inactif37 ;\n" + 
				"    les médicaments de translecture translationnelle38.\n" + 
				"\n" + 
				"Financement de programmes de recherche\n" + 
				"\n" + 
				"2 fondations américaines sont les premiers contributeurs des programmes de recherche sur la maladie et les traitements :\n" + 
				"\n" + 
				"    l'International Rett Syndrome Foundation (Rettsyndrome.org) (40 millions de dollars depuis 1998)39 ;\n" + 
				"    le Rett Syndrome Research Trust (41 millions de dollars depuis 2008)40.\n" + 
				"\n" + 
				"Congrès internationaux\n" + 
				"\n" + 
				"Congrès européens :\n" + 
				"\n" + 
				"    2009 : Milan (Italie)\n" + 
				"    2011 : Kazan (Russie)\n" + 
				"    2013 : Maastricht (Pays-Bas)\n" + 
				"    2015 : Rome (Italie)\n" + 
				"    2017 : Berlin41 (Allemagne)\n" + 
				"\n" + 
				"Congrès mondiaux :\n" + 
				"\n" + 
				"    1984 : Vienne (Autriche)\n" + 
				"    1988 : Vienne (Autriche)\n" + 
				"    1993 : Anvers (Belgique)\n" + 
				"    1996 : Göteborg (Suède)\n" + 
				"    2000 : Nagano (Japon)\n" + 
				"    2008 : Paris (France)\n" + 
				"    2012 : La Nouvelle-Orléans (USA)\n" + 
				"    2016 : Kazan (Russie)42\n" + 
				"    2020 : Sydney (Australie)43\n" + 
				"\n" + 
				"Soutien caritatif\n" + 
				"\n" + 
				"En 1993, l'association internationale du syndrome de Rett reçoit 25 000 dollars de la part du groupe Pearl Jam44.\n" + 
				"\n" + 
				"La même année, un album de 8 titres des Allman Brothers est vendu en édition limitée (15 000 exemplaires) au bénéfice de cette association45.\n" + 
				"\n" + 
				"L'actrice Julia Roberts plaide en octobre 2002 devant une commission du Congrès américain en faveur d'une augmentation des crédits pour la recherche sur cette maladie46. Elle a auparavant participé à un documentaire pour la chaîne Discovery Health Channel47.\n" + 
				"Histoire\n" + 
				"\n" + 
				"Le pédiatre Andreas Rett (1924-1997) est le premier à avoir décrit ce syndrome en 196648.\n" + 
				"\n" + 
				"En octobre 1983, le neuropédiatre suédois Bengt Hagberg (1923-2015) décrit le symptôme chez 35 patients dans les Annales de Neurologie49. Il donne le nom d'Andreas Rett à la maladie.\n" + 
				"\n" + 
				"En juin 1992, Adrian Bird découvre la protéine MeCP250.\n" + 
				"\n" + 
				"Son caractère de maladie génétique a été mis en évidence en 1999 par l'équipe du professeur Huda Zoghbi de l'Institut de recherche neurologique de Houston51.\n" + 
				"Notes et références\n" + 
				"\n" + 
				"    ↑ INSERM US14 -- TOUS DROITS RESERVES, « Orphanet: Syndrome de Rett » [archive], sur www.orpha.net (consulté le 15 février 2017)\n" + 
				"    ↑ Friederike Ehrhart, Nasim Bahram Sangani et Leopold M. G. Curfs, « Current developments in the genetics of Rett and Rett-like syndrome », Current Opinion in Psychiatry,‎ 4 décembre 2017 (ISSN 1473-6578, PMID 29206688, DOI 10.1097/YCO.0000000000000389, lire en ligne [archive])\n" + 
				"    ↑ « LE SYNDROME DE RETT » [archive], sur www.germaco.net (consulté le 16 février 2017).\n" + 
				"    ↑ https://www.ejpn-journal.com/article/S1090-3798(17)30116-2/fulltext [archive]\n" + 
				"    ↑ http://www.aaiddjournals.org/doi/10.1352/1934-9556-55.6.419?code=aamr-site [archive]\n" + 
				"    ↑ https://theconversation.com/clinicians-make-mistakes-about-intellectual-impairments-as-new-rett-syndrome-findings-show-95085?utm_source=Copy+of+Spectrum+News+%28Daily+Report%29&utm_campaign=1af38c0f71-EMAIL_CAMPAIGN_2018_04_22&utm_medium=email&utm_term=0_5d6f652fd5-1af38c0f71- [archive]\n" + 
				"    ↑ Jacky Guy, Jian Gan, Jim Selfridge et Stuart Cobb, « Reversal of neurological defects in a mouse model of Rett syndrome », Science (New York, N.Y.), vol. 315, no 5815,‎ 23 février 2007, p. 1143–1147 (ISSN 1095-9203, PMID 17289941, DOI 10.1126/science.1138389, lire en ligne [archive]).\n" + 
				"    ↑ « Syndrome de Rett - Thérapie Génique » [archive], sur www.germaco.net (consulté le 24 mars 2017).\n" + 
				"    ↑ http://www.cnrs.fr/inc/communication/direct_labos/bultel.htm [archive] Elle affecte spécifiquement les filles et c’est une des principales causes de retard mental dans le monde avec 1/10000 naissances environ\n" + 
				"    ↑ Dana Rodriguez, « Congrès polyhandicap de 2005 » [archive], dans \"Le polyhandicap au quotidien: Guide à l'usage des aides médico-psychologiques\" de Catherine Derouette (consulté le 16 février 2017)\n" + 
				"    ↑ Rett Syndrome Diagnostic Criteria Work Group (1988) Diagnostic criteria for Rett syndrome. Ann Neurol 23:425-8\n" + 
				"    ↑ (en) Julu PO, Engerström IW, Hansen S, Apartopoulos F, Engerström B, Pini G, Delamont RS, Smeets EE, « Cardiorespiratory challenges in Rett's syndrome » [archive] Lancet 2008;371:1981-3.\n" + 
				"    ↑ INSERM US14 -- TOUS DROITS RESERVES, « Orphanet: Syndrome de Rett » [archive], sur www.orpha.net (consulté le 16 février 2017)\n" + 
				"    ↑ Meir Lotan, Joav Merrick, Isack Kandel et Mohammed Morad, « Aging in Persons with Rett Syndrome: An Updated Review », The Scientific World JOURNAL, vol. 10,‎ 4 mai 2010, p. 778–787 (ISSN 1537-744X, DOI 10.1100/tsw.2010.79, lire en ligne [archive])\n" + 
				"    ↑ (en) « Rett syndrome and aging (PDF Download Available) » [archive], sur ResearchGate (consulté le 28 mars 2017)\n" + 
				"    ↑ (en) Inspired by love (blog de Karly Wahlin) [archive]\n" + 
				"    ↑ Site de l’association française du syndrome de Rett [archive]\n" + 
				"    ↑ (en) Jean-Christophe Roux, Diana Zala, Nicolas Panayotis, Ana Borges-Correia, Frédéric Saudou, Laurent Villard, « Modification of Mecp2 dosage alters axonal transport through the Huntingtin/Hap1 pathway », Neurobiology of disease, vol. 45, no 2,‎ février 2012, p. 786-795 (ISSN 1095-953X, PMID 22127389, DOI 10.1016/j.nbd.2011.11.002, lire en ligne [archive] [PDF])\n" + 
				"    ↑ Fiche descriptive [archive] de l'essai clinique sur ClinicalTrials.gov\n" + 
				"    ↑ (en) Miriam Kron, C. James Howell, Ian T. Adams, Michael Ransbottom, Diana Christian, Michael Ogier, David M. Katz, « Brain Activity Mapping in Mecp2 Mutant Mice Reveals Functional Deficits in Forebrain Circuits, Including Key Nodes in the Default Mode Network, that are Reversed with Ketamine Treatment », The Journal of Neuroscience, vol. 32, no 40,‎ 3 octobre 2012, p. 13860-13872 (ISSN 0270-6474 et 1529-2401, DOI 10.1523/JNEUROSCI.2159-12.2012, lire en ligne [archive])\n" + 
				"    ↑ (en) Entretien entre le Pr Katz et Monica Coenraads, directrice du Rett Syndrome Research Trust|[1] [archive]\n" + 
				"    ↑ (en) « An Exploratory Trial of Ketamine for the Treatment of Rett Syndrome - Full Text View - ClinicalTrials.gov » [archive], sur clinicaltrials.gov (consulté le 28 mars 2017)\n" + 
				"    ↑ « syndrome de rett » [archive], sur www.germaco.net (consulté le 3 avril 2017)\n" + 
				"    ↑ « 4th European Congress on Rett Syndrome – Full report | Rett Syndrome Europe » [archive], sur www.rettsyndrome.eu (consulté le 7 janvier 2017)\n" + 
				"    ↑ « Newron begins STARS study of sarizotan to treat Rett syndrome » [archive], sur Drug Development Technology (consulté le 7 janvier 2017)\n" + 
				"    ↑ (en) « Evaluation of the Efficacy, Safety, and Tolerability of Sarizotan in Rett Syndrome With Respiratory Symptoms - Full Text View - ClinicalTrials.gov » [archive], sur clinicaltrials.gov (consulté le 28 mars 2017)\n" + 
				"    ↑ (en) « IGF1 as a Potential Treatment for Rett Syndrome: Safety Assessment in Six Rett Patients » [archive], sur hindawi.com.\n" + 
				"    ↑ (en) « Treatment of Rett Syndrome With rhIGF-1 (Mecasermin [rDNA]Injection) » [archive], sur ClinicalTrials.gov.\n" + 
				"    ↑ (en) « A Safety Study of NNZ-2566 in Patients With Rett Syndrome » [archive], fiche descriptive de l'essai clinique de la molécule NNZ-2566, sur ClinicalTrials.gov.\n" + 
				"    ↑ (en) « Neuren’s NNZ-2566 successful in demonstrating clinical benefit in Rett syndrome Phase 2 trial » [archive], sur neurenpharma.com, 12 novembre 2014 (consulté le 7 janvier 2017)\n" + 
				"    ↑ (en) « Neuren's Phase 2 trial of trofinetide demonstrates significant clinical benefit in pediatric Rett syndrome » [archive], sur neurenpharma.com, 22 mars 2017 (consulté le 24 mars 2017)\n" + 
				"    ↑ (en) Noël C. Derecki, James C. Cronk, Zhenjie Lu, Eric Xu, Stephen B. G. Abbott, Patrice G. Guyenet, Jonathan Kipnis, « Wild-type microglia arrest pathology in a mouse model of Rett syndrome », Nature, vol. 484, no 7392,‎ 5 avril 2012, p. 105-109 (ISSN 0028-0836, PMID 22425995, DOI 10.1038/nature10907, lire en ligne [archive])\n" + 
				"    ↑ « Paper describes results of BMT in models of Rett Syndrome » [archive], sur News-Medical.net, 19 mars 2012 (consulté le 3 avril 2017)\n" + 
				"    ↑ (en) Stuart R. Cobb, « Cognitive disorders: Deep brain stimulation for Rett syndrome », Nature, vol. 526, no 7573,‎ 15 octobre 2015, p. 331–332 (ISSN 0028-0836, DOI 10.1038/526331a, lire en ligne [archive])\n" + 
				"    ↑ (en) Guy J, Gan J, Selfridge J, Cobb S, Bird A. « Reversal of neurological defects in a mouse model of Rett syndrome » [archive] Science 2007;315:1143-7.\n" + 
				"    ↑ (en) « A codon-optimized Mecp2 transgene corrects breathing deficits and improves survival in a mouse model of Rett syndrome » [archive], sur ResearchGate (consulté le 3 avril 2017)\n" + 
				"    ↑ (en-US) « Muting X chromosome silencers may serve as remedy for Rett | Spectrum », Spectrum,‎ 30 mars 2017 (lire en ligne [archive])\n" + 
				"    ↑ Kamal KE Gadalla, Paul D Ross, Ralph D Hector et Noha G Bahey, « Gene therapy for Rett syndrome: prospects and challenges », Future Neurology, vol. 10, no 5,‎ 9 octobre 2015, p. 467–484 (ISSN 1479-6708, DOI 10.2217/fnl.15.29, lire en ligne [archive])\n" + 
				"    ↑ (en-US) « About the Foundation - Our Impact - Rettsyndrome.org » [archive], sur www.rettsyndrome.org (consulté le 24 mars 2017)\n" + 
				"    ↑ (en-US) « About Us - Rett Syndrome Research Trust » [archive], sur reverserett.org (consulté le 24 mars 2017)\n" + 
				"    ↑ (en) « 5. European Rett Syndrome Congress 2017 » [archive], sur www.rett2017.berlin (consulté le 31 mars 2017)\n" + 
				"    ↑ « VIII WORLD RETT SYNDROME CONGRESS » [archive], sur www.worldcongress2016.rettsyndrome.ru (consulté le 31 mars 2017)\n" + 
				"    ↑ (en) « The Rett Syndrome Association of Australia (RSAA) is interested in hosting the 9th World Congress on Rett Syndrome in Sydney in October 2020.To help us in making this decision, RSAA is seeking your input via completion of this survey by 16/01/17. » [archive], sur www.surveymonkey.com (consulté le 31 mars 2017)\n" + 
				"    ↑ (en) « Pearl Jam donates to Rett syndrom » [archive] The Tuscaloosa News, publié le 22 décembre 1993, consulté le 24 septembre 2012.\n" + 
				"    ↑ The Allman Brothers Band, IRSA International Rett Syndrome Association [archive], 1993.\n" + 
				"    ↑ (en) CNN Politics, Julia Roberts chokes back tears before Congress [archive], publié le 9 mai 2002, consulté le 24 septembre 2012.\n" + 
				"    ↑ http://aboutjulia.com/2012/01/videos-julia-roberts-in-silent-angels-2000-and-his-way-2011/ [archive]\n" + 
				"    ↑ (en) Article « Syndrome de Rett » [archive] sur le site « Who Named It? »\n" + 
				"    ↑ (en) Bengt Hagberg, Jean Aicardi, Karin Dias et Ovidio Ramos, « A progressive syndrome of autism, dementia, ataxia, and loss of purposeful hand use in girls: Rett's syndrome: Report of 35 cases », Annals of Neurology,‎ octobre 1983 (lire en ligne [archive])\n" + 
				"    ↑ (en) Joe D. Lewis, Richard R. Meehan, William J. Henzel, Ingrid Maurer-Fogy, Peter Jeppesen, Franz Klein et Adrian Bird, « Purification, sequence, and cellular localization of a novel chromosomal protein that binds to Methylated DNA », Cell,‎ juin 1992 (lire en ligne [archive])\n" + 
				"    ↑ (en) Ruthie E. Amir, Ignatia B. Van den Veyver, Mimi Wan et Charles Q. Tran, « Rett syndrome is caused by mutations in X-linked MECP2, encoding methyl-CpG-binding protein 2 », Nature Genetics, vol. 23, no 2,‎ 1er octobre 1999, p. 185–188 (ISSN 1061-4036, DOI 10.1038/13810, lire en ligne [archive])\n" + 
				"\n" + 
				"Voir aussi\n" + 
				"Articles connexes\n" + 
				"\n" + 
				"    Troubles du spectre autistique\n" + 
				"\n" + 
				"Liens externes\n" + 
				"\n" + 
				"    Fiche grand public d'Orphanet [archive] Document utilisé pour la rédaction de l’article\n" + 
				"    Association française du syndrome de Rett [archive]\n" + 
				"    Le syndrome de Rett sur www.autisme.qc.ca [archive]\n" + 
				"    (en) Online Mendelian Inheritance in Man, OMIM (TM). Johns Hopkins University, Baltimore, MD. MIM Number:312750 [3] [archive]\n" + 
				"    (en) GeneTests: Medical Genetics Information Resource (database online). Copyright, University of Washington, Seattle. 1993-2005 [4] [archive]\n" + 
				"    (en) Practical genetics [archive] dans European Journal of Human Genetics (2006) 14, 896–903 :\n" + 
				"    (en) International Rett Syndrome Foundation [archive]\n" + 
				"    (en) Rett Syndrome Research Trust [archive]\n" + 
				"    (en) Rett Syndrome Association [archive] (Royaume-Uni)\n" + 
				"    (en) Rettgirl.org [archive], site américain d'informations sur des produits, échange de conseils entre familles, ressources sur l'alimentation, thérapies\n" + 
				"\n" + 
				" [afficher]\n" + 
				"v · m\n" + 
				"Troubles mentaux et du comportement (CIM-10 : F00-F99)\n" + 
				" [afficher]\n" + 
				"v · m\n" + 
				"Troubles du spectre de l'autisme (DSM-5) et troubles envahissants du développement (DSM-IV + CIM-10)\n" + 
				"\n" + 
				"    Portail de la médecine Portail de la médecine Portail du handicap Portail du handicap Portail de la psychologie Portail de la psychologie Portail de l’autisme Portail de l’autisme \n" + 
				"\n" + 
				"Catégories :\n" + 
				"\n" + 
				"    Maladie génétique du système nerveuxÉpilepsieSyndrome d'origine génétiqueTroubles du développement et des fonctions instrumentalesForme de handicap\n" + 
				"\n" + 
				"[+]\n" + 
				"Menu de navigation\n" + 
				"\n" + 
				"    Non connecté\n" + 
				"    Discussion\n" + 
				"    Contributions\n" + 
				"    Créer un compte\n" + 
				"    Se connecter\n" + 
				"\n" + 
				"    Article\n" + 
				"    Discussion\n" + 
				"\n" + 
				"    Lire\n" + 
				"    Modifier\n" + 
				"    Modifier le code\n" + 
				"    Voir l’historique\n" + 
				"\n" + 
				"Rechercher\n" + 
				"\n" + 
				"    Accueil\n" + 
				"    Portails thématiques\n" + 
				"    Article au hasard\n" + 
				"    Contact\n" + 
				"\n" + 
				"Contribuer\n" + 
				"\n" + 
				"    Débuter sur Wikipédia\n" + 
				"    Aide\n" + 
				"    Communauté\n" + 
				"    Modifications récentes\n" + 
				"    Faire un don\n" + 
				"\n" + 
				"Outils\n" + 
				"\n" + 
				"    Pages liées\n" + 
				"    Suivi des pages liées\n" + 
				"    Importer un fichier\n" + 
				"    Pages spéciales\n" + 
				"    Lien permanent\n" + 
				"    Informations sur la page\n" + 
				"    Élément Wikidata\n" + 
				"    Citer cette page\n" + 
				"\n" + 
				"Imprimer / exporter\n" + 
				"\n" + 
				"    Créer un livre\n" + 
				"    Télécharger comme PDF\n" + 
				"    Version imprimable\n" + 
				"\n" + 
				"Dans d’autres langues\n" + 
				"\n" + 
				"    العربية\n" + 
				"    Català\n" + 
				"    Deutsch\n" + 
				"    English\n" + 
				"    Español\n" + 
				"    Italiano\n" + 
				"    Nederlands\n" + 
				"    Português\n" + 
				"    中文\n" + 
				"\n" + 
				"Modifier les liens\n" + 
				"\n" + 
				"    La dernière modification de cette page a été faite le 24 octobre 2018 à 01:59.\n" + 
				"    Droit d'auteur : les textes sont disponibles sous licence Creative Commons attribution, partage dans les mêmes conditions ; d’autres conditions peuvent s’appliquer. Voyez les conditions d’utilisation pour plus de détails, ainsi que les crédits graphiques. En cas de réutilisation des textes de cette page, voyez comment citer les auteurs et mentionner la licence.\n" + 
				"    Wikipedia® est une marque déposée de la Wikimedia Foundation, Inc., organisation de bienfaisance régie par le paragraphe 501(c)(3) du code fiscal des États-Unis.\n" + 
				"\n" + 
				"    Politique de confidentialité\n" + 
				"    À propos de Wikipédia\n" + 
				"    Avertissements\n" + 
				"    Contact\n" + 
				"    Développeurs\n" + 
				"    Déclaration sur les témoins (cookies)\n" + 
				"    Version mobile\n" + 
				"\n" + 
				"    Wikimedia Foundation	\n" + 
				"    Powered by MediaWiki	\n" + 
				"\n" + 
				"";
		drugDetectionExample(sentence);
		
		bratOutputExample(sentence);
	}
}
