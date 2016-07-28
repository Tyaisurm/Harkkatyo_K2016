CREATE TABLE "Object"(
	"Description" VARCHAR(255) NOT NULL,
	"Weight" REAL NOT NULL,
	"Size"	INTEGER NOT NULL,
	"Name" VARCHAR(255) NOT NULL,
	"Fragile" INTEGER NOT NULL,
	"ObjectID" INTEGER,

	PRIMARY KEY (ObjectID)
);
CREATE TABLE "PacketType"(
	"ClassID" INTEGER NOT NULL,
	"Description" VARCHAR(255) NOT NULL,
	"TravelLimit" REAL NOT NULL,
	"SizeLimit" INTEGER NOT NULL,

	PRIMARY KEY (ClassID)
);
CREATE TABLE "History"(
	"DeliveryID" INTEGER,
	"SentDate" TIMESTAMP NOT NULL,
	"ObjectName" VARCHAR(255),
	"FragileStatus" INTEGER NOT NULL,
	"FromPost" VARCHAR(255) NOT NULL,
	"ToPost" VARCHAR(255) NOT NULL,
	"ClassID" INTEGER NOT NULL,
	"ObjectWeight" REAL NOT NULL,

	PRIMARY KEY(DeliveryID),
	FOREIGN KEY(ClassID) REFERENCES PacketType(ClassID)
);
CREATE TABLE "Smartpost"(
	"PostID" INTEGER,
	"PostDescription" VARCHAR(255) NOT NULL,
	"Availability" VARCHAR(255) NOT NULL,

	PRIMARY KEY(PostID)
);
CREATE TABLE "AddressSP"(
	"PostID" INTEGER,
	"Postcode" INTEGER NOT NULL,
	"StreetAddress" VARCHAR(255) NOT NULL,
	"City" VARCHAR(255) NOT NULL,

	PRIMARY KEY(PostID),
	FOREIGN KEY(PostID) REFERENCES SmartPost(PostID) ON DELETE CASCADE
);
CREATE TABLE "CoordinatesSP"(
	"PostID"INTEGER,
	"Lat" VARCHAR(255) NOT NULL,
	"Lng" VARCHAR(255) NOT NULL,

	PRIMARY KEY(PostID),
	FOREIGN KEY(PostID) REFERENCES Smartpost(PostID) ON DELETE CASCADE

);
CREATE TABLE "Warehouse"(
	"PacketID" INTEGER,
	"Class" INTEGER NOT NULL,
	"ObjectID" INTEGER NOT NULL,
	"ToPostID"INTEGER NOT NULL,
	"FromPostID" INTEGER NOT NULL,

	PRIMARY KEY (PacketID),
	FOREIGN KEY (Class) REFERENCES PacketType(ClassID),
	FOREIGN KEY (ToPostID) REFERENCES Smartpost(PostID),
	FOREIGN KEY (FromPostID) REFERENCES Smartpost(postID),
	FOREIGN KEY (ObjectID) REFERENCES Object(ObjectID)
);

INSERT INTO "Object" VALUES('Kestävä muki. Kunkin kahvinjuojan sankari.',0.2,2,'DogeMugi2.0',0,NULL);
INSERT INTO "Object" VALUES('Heikko kassi. Päivittäin vaihdettava, mutta hieno',2.0,3,'CateKassi1.0',1,NULL);
INSERT INTO "Object" VALUES('Kylpijän vakiovaruste',0.1,1,'Kylpyankka Vaakku',0,NULL);
INSERT INTO "Object" VALUES('Iso, äänekäs ja mahtava! Käsittele silti varoen.',100.0,9,'Pikimusta piano',1,NULL);

INSERT INTO "PacketType" VALUES(1,'Pikakyyti',150.0,10),(2,'Turvakyyti',-1.0,5),(3,'Stressinpurku',-1.0,10);
