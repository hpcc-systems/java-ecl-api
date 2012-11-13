IMPORT Std;


//Declare the format of the source and destination record

Layout_People := RECORD
  STRING15 FirstName;
  STRING25 LastName;
  STRING15 MiddleName;
  STRING5 Zip;
  STRING42 Street;
  STRING20 City;
  STRING2 State;
END;

//Declare reference to source file

File_OriginalPerson :=
DATASET('~OriginalPerson',Layout_People,THOR);


//Write the Transform code
Layout_People toUpperPlease(Layout_People pInput)
:= TRANSFORM
  SELF.FirstName := Std.Str.ToUpperCase(pInput.FirstName);
  SELF.LastName := Std.Str.ToUpperCase(pInput.LastName);
  SELF.MiddleName := Std.Str.ToUpperCase(pInput.MiddleName);
  SELF.Zip := pInput.Zip;
  SELF.Street := pInput.Street;
  SELF.City := pInput.City;
  SELF.State := pInput.State;
END ;

TransformedPersonDataset := PROJECT(File_OriginalPerson,toUpperPlease(LEFT));
IsRedundantPerson := TransformedPersonDataset.FirstName = TransformedPersonDataset.LastName;
IsFirstNameFlavio := TransformedPersonDataset.FirstName = 'FLAVIO';

//OUTPUT(TOPN(TransformedPersonDataset,10,firstname));

OUTPUT(COUNT(TransformedPersonDataset));
OUTPUT(TransformedPersonDataset(IsRedundantPerson));
OUTPUT(TransformedPersonDataset(IsFirstNameFlavio));