# Sistem de Gestionare a Rețelei de Biblioteci

## Descriere Scurtă
Un sistem de gestionare pentru o rețea de biblioteci care permite utilizatorilor să împrumute, să returneze și să rezerve cărți.

## Funcții Principale

### 1. Gestionarea Inventarului de Cărți
- **Informații despre cărți**: Stocarea datelor precum titlul, autorul, anul publicării, genul și locația în bibliotecă.
- **Actualizarea inventarului**: Atunci când cărțile sunt împrumutate sau returnate.
- **Urmărirea disponibilității**: În funcție de starea de împrumut.

### 2. Managementul Cititorilor
- **Detalii cititori**: Stocarea informațiilor precum numele, contactul și istoricul împrumuturilor.
- **Autentificare**: Permiterea cititorilor să creeze conturi și să se autentifice.
- **Verificare**: La momentul împrumutului.

### 3. Procesul de Împrumut
- **Căutare**: Permiterea cititorilor să caute cărți după titlu, autor și alte criterii.
- **Listare cărți**: Afișarea cărților disponibile conform criteriilor de căutare.
- **Proces împrumut**: Selectarea, calculul duratei, penalități pentru întârziere.
- **Confirmare**: Oferirea unui ID unic de împrumut.
- **Actualizare inventar**: Marcarea cărții ca împrumutată.

### 4. Managementul Împrumuturilor
- **Vizualizare împrumuturi**: Permiterea cititorilor să vizualizeze sau să modifice împrumuturile lor.
- **Gestionare conflicte**: În cazul în care o carte este deja rezervată sau împrumutată.
- **Notificări**: Trimiterea de mesaje către cititori despre starea împrumuturilor lor.

#### Database Schema:
![db Schema](https://github.com/Vladdstian/libraryManagementSystem/blob/main/db_schema.png?raw=true
)

Read this in other languages: [English](README.md) 