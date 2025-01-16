# 🚀 Proyecto Java + Docker

¡Bienvenido al proyecto! Este README te guiará para configurar y ejecutar la aplicación en tu entorno local.

---

## 🛠️ Prerrequisitos

Antes de comenzar, asegúrate de tener lo siguiente instalado en tu sistema:

1. **Java 21**
2. **Docker**
3. **Puerto 8080** libre

Además, sigue estos pasos previos:
- Clona este repositorio en tu máquina local.
- Abre una terminal y navega a la carpeta del repositorio clonado.

---

## ⚙️ Configuración y Ejecución

1. Ejecuta el siguiente comando en la terminal dentro de la ruta del repositorio:
   ```bash
   docker-compose up --build
2. UIngresa a la siguiente Url:
   ```bash
   http://localhost:8080/swagger-ui/index.html

## ⚙️ Notas
Se utilizó la dependencia Flyway para gestionar el versionado de la base de datos, garantizando un control eficiente y ordenado de los cambios en los esquemas.

Además, se implementó la arquitectura Hexagonal (también conocida como Arquitectura de Puertos y Adaptadores), lo que permite una mayor flexibilidad al facilitar el intercambio o actualización de tecnologías en los adaptadores sin afectar el núcleo de la aplicación.