class Transport: 
    def __init__(self, ID, tipoMezzo, nomeMezzo, tratta, attivo, compagnia):
        self.ID = ID
        self.tipoMezzo = tipoMezzo
        self.nomeMezzo = nomeMezzo
        self.tratta = tratta
        self.attivo = attivo
        self.compagnia = compagnia
        self.posX = None
        self.posY = None

