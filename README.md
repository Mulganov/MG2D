# MG2D
Первое что хочу сказать это, СПАСИБО!!! Если ты это видишь значат тебя заинтересовала моя графическая библиотека. 
Хочу сразу все объяснить, она основана на SurFaceView - это значат что с этой библиотекой нельзя юзать другие SurFaceView'ы.

В этом проекте есть Демо в котором на практике показано как работать с mg2d.

## Класс MG2D и его методы

Начнем самого начала, инициализации mg2d не требует, что бы начать работать с mg2d нужно создать его, new MG2D(context).
Патом нужно его "засунуть" в контент activity. Что бы получить view c mg2d mg2d.getView().

Начнем разбор архитектуры с нашого экземплятара mg2d. 
В нем есть настройки, которые являются глобальними(mg2d.getSetting()).

В настройках можно установить размер экрана, сложность идет в том что есть разные пропорции экрана(16:9 и 17:9),
по этому нужно уточнять для mg2d что мы хотим работать с разными экранами, на данный момент можно работать с пропорциями 4:3(16:9),
что бы графика коректно работала и с экранами 17:9 нужно добавить граници(border).
Что бы создать обычный Size есть конструкто:
<code> mg2d.getSetting().setSize(new Scena.Size(Scena.Size.Format.FORMAT_16X9, activity)); </code>

Следущий пункт в настройках - это border. Они будут видны только на экранах 17:9. У бордера есть цвет, и так же его можно вкл. или выкл.

Дальше, можно установить фон для графики .setBackground(int background).

И последний пункт - это ControlManager. ControlManager нужен для глобальных евентов(сенсор екрана). В нем можно зарегистрировать событие ил удалить.
Что бы создать событие нужно создать его интерфейс. В onClickWindow все просто:


      MG2D.getMG2D().getSetting().getControlManager().getRegister().addClickDownWindow(new ControlManager.ClickEvent() {
            @Override
            public void Event(ControlManager.ClickEvent.Info clickInfo) {
                
            }
        });
 
      public ControlManager.ClickEvent.Info(float x, float y, long delta){
                this.x = x;                       // x - точка клика
                this.y = y;                       // y - точка клика
                this.delta = delta;               // delta - сколько прошло милисекунд между: начало отрисовки и конец отрисовки графики
      }
    
Кроме "кликов" в контролере есть onMove:

    MG2D.getMG2D().getSetting().getControlManager().getRegister().addMoveWindow(new ControlManager.MoveEvent() {
            @Override
            public void Event(ControlManager.ClickEvent.Info clickInfo, Info moveInfo) {
                
            }
        });
        
        public Info(float x, float y, long delta){
                this.x = x;             // x - разница между перед последним и последним касанием
                this.y = y;             // y - разница между перед последним и последним касанием
                this.delta = delta;
            }

На этом класс MG2D закончен.

## Графика, как же начать рисовать
### Scena
В mg2d все отрисовка идет только через сцены, так что перед тем как смотреть что можно рисовать нужно понть как это памто засунть в mg2d.
Что бы создать сцену надо иметь activity i mg2d:

        s1= new Scena(this, mg2d);
        
Что же можно делать с сценой? Можно добавить граф. елемент(add(graph)), и удалить (remove(graph)).
Когда сцена готова ее нужно "закинуть" в mg2d:

        mg2d.setScena(s1);
        
На этом все.
### Graph - главный и базовый класс графики
Эта библиотека была сделана и ее развитие идет только из-за ее удобности именно в плане разработки графики и ее взаемодествие с юзером.
Все следйщие графические классы будут унаследованы от Graph. Graph максимально абстрактый, по этому юзер может его модифицировать  как захочет.
Основное что есть в Graph - это его ключ(можете не заморачиватся, пишите "auto"), индекс в сцене, координаты(x, y) и базовые интерфейсы: Draw, Click(Down, Up), Move.
Так же сть их флаги работы, (название) + "В", например, можно ли рисовать графику? можно установить значение - setDrawB(boolean).
С евентами так же.
Пример установление Draw:

      graph.setDraw(new Graph.Draw() {
            @Override
            public void draw(Canvas canvas) {
                canvas.draw...
            }
        });
        
Пример с ClickUp(так же и другие евенты):

      super.setClickUp(new ClickUp() {
            @Override
            public void onClick(Click.Event event) {
                    getCircle().setX(getCx());
                    getCircle().setY(getCy());
            }
        });
        
        
### Перечисления всей графики которая доступна на данный момент
#### Pixel

      Pixel(String key,  int x,  int y,  Paint p)
      
С пикселем все понятно

### Пакет с фигурами
Не чего особеного просто опишу все конструкторы
#### Arc

    Arc(String key,  int x,  int y,  int w,  int h,  float startAngle,  float sweepAngle,  boolean useCenter,  Paint p)


#### Circle

    Circle(String key, final int x, final int y, final int r, final Paint p)
    
#### Line

    Line(String key, int x0,  int y0,  int x1,  int y1,  Paint p)
    
#### Rect

  Rect(String key, final int x, final int y, final int w, final int h, final Paint p)

### Image

    Image(String key, final Bitmap bitmap, mg2dMatrix matrix)
    
#### mg2dMatrix

     mg2dMatrix(int tx, int ty, int w, int h)
     
mg2dMatrix - по сути устанавливает размеры картинки.

    Tx i Ty == X i Y
    
Пока что картинку нельзя крутить по оси и все такое, только отрисовать и все евенты.

### Пакет Template
#### Joystick

    Joystick(String key, Circle circle)
    

Это обычный джойстик, что бы получать с него значение у него есть свой евент:

    public interface JoystickEvent{
        void Event(float x, float y, long delta);
    }

X i Y - это числа 0..1

Так же в джойстике есть радиусы действия: активный и обычный. Обычный это граници для чисел 0..1 . Активный - это в каком радиусе будет срабатывать JoystickEvent и вообще двигатся джойстик.
Так же можно поменять цвет и все такое.
## Итог и демо

Итог, можно рисовать по сути все что угодно, если чего не хватает в ядре то можно спокойно создать свой.
В демо явно показано как я создавал свой Graph(Camera)

## Если есть вопросы или связь со мной
Telegram: @main_rukozhop
