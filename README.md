# 🌍 Darwin World

**Autor**: Radosław Szepielak, Paweł Saltarius 
**Wariant projektu**: D-4 (Dziki Sowoniedźwiedź + Starość nie radość)  
**Biblioteka GUI**: JavaFX  
**Budowanie projektu**: Gradle  

---

## 🎯 Cel projektu

Projekt ma na celu stworzenie symulacji świata, w którym obserwujemy ewolucję roślinożernych zwierząt w środowisku złożonym z dżungli i stepów. Zwierzęta poruszają się, jedzą, rozmnażają i umierają, a ich genotypy są dziedziczone i mutowane — w ten sposób powstają nowe gatunki. To gra, w którą nie gramy — tylko obserwujemy.

---

## 🧬 Anatomia zwierzaka

Każdy zwierzak posiada:
- współrzędne `(x, y)` na mapie,
- poziom energii (spada codziennie, rośnie po zjedzeniu rośliny),
- kierunek, w którym jest zwrócony (8 możliwych),
- genotyp (ciąg liczb 0–7),
- aktywny gen sterujący ruchem.

Zwierzak codziennie:
1. Obraca się zgodnie z aktywnym genem,
2. Przesuwa się o jedno pole,
3. Aktywuje kolejny gen (cyklicznie).

---

## 🌿 Roślinność

- Mapa zawiera **stepy** (mało roślin) i **dżungle** (dużo roślin).
- Rośliny wyrastają codziennie — preferencyjnie na określonych obszarach (np. zalesione równiki).
- Po wejściu zwierzaka na pole z rośliną — roślina znika, energia zwierzaka rośnie.

---

## ❤️‍🔥 Rozmnażanie

- Zwierzęta mogą się rozmnażać, jeśli mają wystarczająco dużo energii.
- Potomek dziedziczy genotyp jako krzyżówkę genotypów rodziców.
- Zachodzi mutacja (pełna losowość).
- Rodzice przekazują część swojej energii potomstwu.

---

## ⚙️ Przebieg symulacji (na dzień)

1. Usunięcie martwych zwierząt,
2. Ruch wszystkich zwierząt,
3. Konsumpcja roślin,
4. Rozmnażanie,
5. Wzrost nowych roślin.

---

## 🔧 Konfigurowalne parametry

- Rozmiar mapy
- Typ mapy (kula ziemska, dziki sowoniedźwiedź, itp.)
- Startowa liczba zwierząt i roślin
- Energia startowa, energia z rośliny, energia potrzebna do rozmnażania
- Długość genomu
- Liczba i typ mutacji
- Warianty zachowań i mutacji

---

## 🐻 Wariant D: Dziki Sowoniedźwiedź

- Część mapy (20%) to terytorium sowoniedźwiedzia.
- Porusza się jak inne zwierzęta (losowy genotyp).
- Jest nieśmiertelny, nie zjada roślin.
- Zabija każde zwierzę, które stanie na jego polu.

## 🧓 Wariant 4: Starość nie radość

- Starsze zwierzęta poruszają się rzadziej.
- Szansa pominięcia ruchu rośnie z wiekiem (do 80%).

---

## 🖥️ Interfejs użytkownika (JavaFX)

- Możliwość wyboru gotowej lub własnej konfiguracji,
- Start wielu symulacji równocześnie (każda w osobnym oknie),
- Animowana mapa:
  - Zwierzęta (energia jako kolor lub pasek),
  - Rośliny,
- Pauza / wznowienie,
- Statystyki dzienne:
  - liczba zwierząt, roślin, wolnych pól,
  - najpopularniejsze genotypy,
  - średnia energia, długość życia, liczba dzieci.

---

## 🧪 Śledzenie wybranego zwierzaka

- Genom i aktywny gen,
- Energia, wiek, liczba dzieci i potomków,
- Dzień śmierci (jeśli zdechł),
- Liczba zjedzonych roślin.

---

## 📊 Eksport danych

- Możliwość zapisu statystyk do pliku `.csv`,
- Jeden plik CSV na symulację,
- Pliki można otwierać w Excelu, LibreOffice itd.

---
