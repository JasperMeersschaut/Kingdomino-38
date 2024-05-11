use ID429632_kingdominoG38;
create table if not exists Speler(
    gebruikersnaam varchar(50) not null,
    geboortejaar int,
    aantalGewonnenSpellen int,
    aantalGespeeldeSpellen int,
    primary key (gebruikersnaam)
);
