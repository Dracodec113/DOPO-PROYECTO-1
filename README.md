# DOPO-PROYECTO-1

## Descripción

El proyecto en este repositorio es basado en el problema J Stacking Cups de "The 2025 ICPC World Finals Baku". 

---

### Problema

Tienes una colección de **n tazas cilíndricas**, donde la **i-ésima taza** tiene una altura de **2i − 1 cm**.

Las tazas tienen **diámetros crecientes**, de modo que la taza **i** cabe dentro de la taza **j** **si y solo si** $i < j$.

La base de cada taza tiene un **grosor de 1 cm** (lo que hace que la taza más pequeña sea bastante inútil, ya que mide solo 1 cm de alto, pero la conservas por razones sentimentales).

---

### Situación

Después de lavar todas las tazas, las apilas formando una **torre**.

* Cada taza se coloca **en posición vertical** (con la abertura hacia arriba).
* Los **centros de todas las tazas quedan alineados verticalmente**.

---

### Definición de altura de la torre

La **altura de la torre** se define como la **distancia vertical** desde:

* el punto más bajo de cualquiera de las tazas
* hasta el punto más alto

---

### Objetivo

Quieres determinar **en qué orden colocar las tazas** para que la **altura final de la torre (en cm)** sea tu número favorito.

**Nota:**
Debes usar **todas las n tazas**.


Aquí tienes la traducción organizada en **Markdown**:

---

## Entrada

La entrada consiste en **una sola línea** que contiene **dos enteros** `n` y `h`, donde:

* **n** (`1 ≤ n ≤ 2 · 10⁵`) es el número de tazas
* **h** (`1 ≤ h ≤ 4 · 10¹⁰`) es tu número favorito

---

## Salida

* Si es posible construir una torre con **altura `h`**, debes imprimir
  las **alturas de todas las tazas en el orden en que deben colocarse**
  para lograr dicha altura.

* En caso contrario, imprime:

```
impossible
```

Si existe **más de un orden válido**, cualquiera será aceptado.

---

### Ejemplos

#### Ejemplo 1

**Entrada**

```
4 9
```

**Salida**

```
7 3 5 1
```

---

#### Ejemplo 2

**Entrada**

```
4 100
```

**Salida**

```
impossible
```

---
## Entregas

Las entregas de este proyecto estarán separadas en ciclos de la siguiente manera:

**S04. Primer ciclo (Sa 14  feb -> Do 15 feb)**
**S06, Segundo ciclo (Sa 28 feb)**
**S08. Tercer ciclo (Sa 14 mar)**
**S10. Cuarto ciclo (Sa 28 mar)**
**S11. Final (Sa 11 abr)**