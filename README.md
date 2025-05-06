# Akinator en Java (versión simple con red neuronal)

Este es un proyecto muy simple que simula un Akinator usando una red neuronal implementada desde cero en Java.

## 🚀 ¿Qué hace?

El programa te hace una serie de 5 preguntas (sí/no) para adivinar un personaje entre varios posibles.

## 🧠 ¿Cómo funciona?

- Usa una red neuronal muy básica con 5 entradas, una capa oculta y 1 salida.
- El usuario puede entrenar al modelo desde cero o cargar uno previamente guardado.
- El entrenamiento se basa en ejemplos sencillos incluidos en el código (`data.java`).

## 📝 Archivos

- `main.java`: Punto de entrada del programa.
- `simpleneuralnetwork.java`: Contiene la red neuronal.
- `data.java`: Contiene los datos de entrenamiento.
- `modelo_akinator.dat`: Archivo binario donde se guarda el modelo entrenado.

## ⚙️ Cómo usar

### Compilar:
```bash

# Akinator Simple en Java v2.0

Este proyecto es una implementación simple del juego Akinator utilizando Java y redes neuronales. El juego hace preguntas para adivinar un personaje basado en las respuestas proporcionadas. Utiliza una red neuronal básica para predecir el personaje basado en las respuestas del usuario.

## Características

- Interfaz gráfica creada con JavaFX.
- Red neuronal entrenada con 5 preguntas y 3 personajes posibles (Pikachu, Goku, Elsa).
- Responde a las preguntas con opciones de "Sí" o "No".
- Después de que el juego termina, se muestra el personaje predicho basado en las respuestas del usuario.
- Opción para terminar el juego y cerrar la ventana.

## Requisitos

- Java 8 o superior.
- Biblioteca JavaFX (se incluye con JDK 8).

## Instrucciones de Uso

1. Clona el repositorio o descarga el código fuente.
2. Compila y ejecuta el proyecto utilizando tu IDE favorito o desde la línea de comandos.
3. El juego te hará una serie de preguntas. Responde con "Sí" o "No".
4. Al final, el juego te mostrará el personaje que predijo la red neuronal.

## Implementación de la Red Neuronal

La red neuronal es muy simple y está configurada de la siguiente manera:
- 5 entradas: las respuestas a las 5 preguntas.
- 5 neuronas en la capa oculta.
- 3 salidas: una para cada personaje (Pikachu, Goku, Elsa).

La red neuronal se entrena utilizando las respuestas del usuario y un conjunto pequeño de datos (5 preguntas, 3 personajes).

### Proceso de entrenamiento

- El entrenamiento de la red neuronal se realiza de forma rápida, utilizando un número bajo de iteraciones (1000).
- El modelo aprende las relaciones entre las respuestas a las preguntas y los personajes posibles.
- Los resultados de la predicción se muestran con 5 decimales de precisión.

## Cómo Contribuir

Si tienes sugerencias para mejorar este proyecto o encuentras algún error, siéntete libre de abrir un **Issue** o enviar un **Pull Request**.

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

