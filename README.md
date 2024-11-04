# Лабораторная работа №3: Работа с базой данных в Android Studio

**Выполнила**: Толстова Кристина
**Язык программирования**: Java

## Цель работы
Изучить работу Android-приложения с базой данных, используя SQLite. В ходе выполнения лабораторной работы будет разработано приложение, которое:

*Создаст базу данных и таблицу при первом запуске.
*Позволит добавлять данные в таблицу и отображать их.
*Обеспечит возможность обновления записей в таблице.

## Задание
### Часть 1:
Разработать приложение, которое взаимодействует с базой данных. Основное активити должно содержать три кнопки:
1. **Показать записи** — открывает новое активити, где выводится информация из таблицы «Одногруппники».
2. **Добавить запись** — добавляет  новую запись в таблицу.
3. **Обновить запись** — изменяет  ФИО в последней записи на «Иванов Иван Иванович».

Условия выполнения:

База данных должна создаваться при первом запуске приложения, если ее еще не существует.
Таблица «Одногруппники» включает следующие поля:
ID (целочисленный с автоинкрементом);
Фамилия, Имя, Отчество (отдельные поля для каждого);
Время добавления записи.

### Часть 2:
Создать новую версию базы данных с обновленной схемой. При изменении версии БД нужно удалить таблицу «Одногруппники» и создать её заново с новой структурой (разделить поле ФИО на три отдельных поля). Убедитесь, что приложение корректно обновляет базу данных при изменении версии.

## Технологии и инструменты
- Платформа: Android Studio
- Язык программирования: Java
- База данных: SQLite
- Использование SQLiteOpenHelper для работы с базой данных

## Архитектура приложения

### 1. Класс `DBHelper`
Этот класс отвечает за создание и управление базой данных. Он включает методы для:

создания базы данных и таблицы;
добавления новых записей;
обновления последней записи;
получения всех данных.

### 2. `MainActivity`
Главное активити приложения с тремя кнопками для работы с базой данных:

Показать записи.
Добавить запись.
Обновить запись.

### 3. `ShowRecordsActivity`
Активити для отображения всех записей из таблицы «Одногруппники». Извлекает данные из базы и отображает их в формате: «Фамилия Имя Отчество - Время добавления».

## Установка и запуск приложения

### Шаг 1: Клонировать проект
```
bash
git clone https://github.com/deaower/labb3
```
### Шаг 2: Откройте проект в Android Studio
Запустите Android Studio.
Выберите Open an existing project и укажите путь к папке с проектом.

### Шаг 3: Запуск проекта
Подключите устройство или настройте эмулятор Android.
Нажмите кнопку Run в Android Studio для сборки и запуска приложения.

## Ключевые классы и методы

### 1. DBHelper
Класс, который управляет базой данных и выполняет операции CRUD (создание, чтение, обновление, удаление).
Пример создания таблицы в методе onCreate:
```
@Override
public void onCreate(SQLiteDatabase db) {
    String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "LastName TEXT, " +
            "FirstName TEXT, " +
            "MiddleName TEXT, " +
            COLUMN_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")";
    db.execSQL(createTable);

    // Вставка начальных данных
    insertInitialData(db);
}
```
### 2. MainActivity
Главное активити, где пользователь может выбрать одно из трех действий: показать записи, добавить запись, обновить запись.

### 3. ShowRecordsActivity
Активити для отображения всех записей. Использует Cursor для чтения данных из базы.

Пример извлечения данных:

```
Cursor cursor = dbHelper.getAllGroupmates();
if (cursor != null && cursor.moveToFirst()) {
    do {
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow("LastName"));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow("FirstName"));
        String middleName = cursor.getString(cursor.getColumnIndexOrThrow("MiddleName"));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("AddTime"));
        builder.append(lastName).append(" ").append(firstName).append(" ").append(middleName).append(" - ").append(time).append("\n");
    } while (cursor.moveToNext());
}
```
Логика обновления базы данных
При изменении версии базы данных, метод onUpgrade() удаляет старую таблицу и создает новую с обновленной схемой:

```
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < 2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Создание новой таблицы
    }
}
```