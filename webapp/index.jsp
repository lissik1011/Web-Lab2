<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>Lab 01</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <header class="header">
      <div class="left_header">
        <h2>Лабораторная работа №2</h2>
        <a class="task" href="task.html">Посмотреть задание</a>
      </div>
      <div class="right_header">
        Львова Елизавета Юрьевна
        <br />
        <strong>Группа:</strong> P3213<br />
        <strong>Вариант:</strong> 1714
      </div>
    </header>

    <div class="container">
      <!-- Слева -->
      <div class="graph-block">
        <p>Радиус R:</p>
        <div class="checkbox">
          <%
            for (float i = 1; i <= 3; i += 0.5) {
              String value = (i == (int)i) ? String.valueOf((int)i) : String.valueOf(i);
              out.println(String.format(
                "<input type='checkbox' name='R' id='r_%s' value='%s' />" +
                "<label for='r_%s'>%s</label>",
                value.replace(".", "_"), value, value.replace(".", "_"), value
              ));
            }
          %>
        </div>

        <canvas
          id="canvas"
          width="380"
          height="380"
          style="border: 0px solid #ccc"
        ></canvas>
      </div>

      <!-- Справа -->
      <div class="text-block">
        <h3>Попади в область!</h3>
        <input type="hidden" id="hiddenR" value="3" name="R">
        <div>
          <label for="X">Координата X: </label>
          <input
            type="text"
            id="X"
            name="X"
            placeholder="Введите число от -5 до 3"
            value="0"
          />
        </div>

        <p>Координата Y:</p>
        <div class="radio">
          <%
            for (int i = -5; i <= 3; i++) {
              out.println(String.format(
                "<input type='radio' name='Y' id='y_%s' value='%s' />" +
                "<label for='y_%s'>%s</label>",
                i, i, i, i
              ));
              if (i == -1) out.println("<br>");
            }
          %>
        </div>
        </p>

        <button class="query">Показать точку</button>
      </div>
    </div>

    <div class="table-block">
      <h3 class="tb_header">История запросов</h3>
      <table id="table">
        <thead>
          <tr>
            <th style="width: 13%">Радиус R</th>
            <th style="width: 13%">Коорд. X</th>
            <th style="width: 13%">Коорд. Y</th>
            <th style="width: 14%">Статус</th>
            <th style="width: 22%">Дата и время</th>
            <th style="width: 27%">Продолжительность обработки</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
    </div>
    <script type="module" src="script.js"></script>
  </body>
</html>
