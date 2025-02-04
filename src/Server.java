import javax.management.MBeanServerConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.net.UnknownHostException;

public class Server extends Thread {
    private static final int PORT = 5555;
    String[] quotes = {
            "Успех - это идти от неудачи к неудаче, не теряя энтузиазма. - Уинстон Черчилль",
            "Не считай дни, извлекай из них пользу. - Мухаммед Али",
            "Не ждите. Время никогда не будет подходящим. - Наполеон Хилл",
            "Неисследованная жизнь не стоит того, чтобы ее жить. - Сократ",
            "Я не потерпел неудачу. Я просто нашел 10 000 способов, которые не работают. - Томас Эдисон",
            "Мотивация - это то, что заставляет вас начать. Привычка - это то, что заставляет вас продолжать. - Джим Рюн",
            "Вы должны выучить правила игры. А затем вы должны играть лучше, чем кто-либо другой. - Альберт Эйнштейн",
            "Если вы потратите свою жизнь на то, чтобы быть лучшим во всем, вы никогда не станете великим ни в чем. - Том Рат",
            "Сначала они не замечают тебя, затем смеются над тобой, потом борются с тобой, а потом ты побеждаешь. - Махатма Ганди",
            "Мечтатели - это спасители мира. - Джеймс Аллен",
            "Лучшая месть - это огромный успех. - Фрэнк Синатра",
            "Измени свои мысли и ты изменишь мир. - Норман Винсент Пил",
            "Упорный труд побеждает талант, когда талант не трудится. - Неизвестный",
            "По моему опыту, существует только одна мотивация, и это - желание.Никакие причины или принципы не могут его сдержать и или противостоять ему. - Джейн Смайли",
            "Мужество - первое из человеческих качеств, потому что это качество, которое гарантирует все остальные. - Уинстон Черчилль",
            "Победа - это еще не все, главное это постоянное желание побеждать. - Винс Ломбарди",
            "Чтобы справиться с собой, используйте голову;    чтобы справиться    с другими, используйте    свое сердце. - Элеонора Рузвельт",
            "Неудача никогда не одолеет меня, если моя решимость добиться успеха достаточно сильна. - Ог Мандино",
            "Я всегда выберу ленивого человека для выполнения сложной работы, потому что он найдет легкий путь ее выполнения. - Билл Гейтс",
            "Жизнь на 10% состоит из того, что с вами происходит, и на 90% из того, как вы на это реагируете. - Чарльз Р. Свиндолл",
            "Ум - это все. Вы становитесь тем, о чем думаете. - Будда",
            "Сначала делайте тяжелую работу. Легкая работа сама о себе позаботится. - Дейл Карнеги",
            "Один из важных ключей к успеху - уверенность в себе. Важный ключ к уверенности в себе - подготовка. - Артур Эш",
            "Если вы поставите перед собой абсурдно высокие цели, и это обернется неудачей, ваша неудача будет выше успеха всех остальных. -Джеймс Кэмерон",
            "Разница между обычным и экстраординарным - в этом небольшом лишнем. - Джимми Джонсон",
            "Будь скромным. Будь голодным. И всегда будь самым усердным в комнате. - Дуэйн Джонсон",
            "Будь собой; все остальные роли уже заняты. — Оскар Уайльд",
            "Чем меньше человек знает, тем громче он говорит. — Михаил Ломоносов",
            "Чтобы победить врага, надо сначала научиться управлять самим собой. — Сунь Цзы",
            "Никогда не откладывай на завтра то, что можешь сделать сегодня. — Бенжамин Франклин",
            "Люди забывают, что ты сказал, люди забывают, что ты сделал, но люди никогда не забудут, как ты заставил их себя чувствовать. — Майя Анджелоу",
            "Жизнь — это то, что происходит, пока мы строим планы. — Джон Леннон",
            "Человек, который никогда не ошибается, никогда не пробует ничего нового. — Альберт Эйнштейн",
            "Все, что нас не убивает, делает нас сильнее. — Фридрих Ницше",
            "Главная причина, по которой люди терпят неудачу в жизни, заключается в том, что они слушают своих друзей, семью и соседей. - Наполеон Хилл",
            "Ограничения живут только в нашем сознании. Но если мы используем свое воображение, наши возможности становятся безграничными. - Джейми Паолинетти",
            "Мужество - это сопротивление страху, овладение страхом, а не его отсутствие. - Марк Твен",
            "Стремитесь быть не успешным человеком, а скорее ценным человеком. - Альберт Эйнштейн",
            "Я не знаю секрета успеха, но секрет неудачи - это попытка всем угодить. - Билл Косби",
            "Лучшее время, чтобы посадить дерево, было 20 лет назад. Другое лучшее время - сейчас. - Китайская пословица",
            "Ваше время ограничено, не тратьте его на чужую жизнь. - Стив Джобс",
            "Успешные люди делают то, что неуспешные люди не хотят делать. Не желай, чтобы было легче, желай стать лучше.  - Джим Рон",
            "Если вы не можете делать великие дела, делайте маленькие дела по-великому.  - Наполеон Хилл",
            "Будь изменой, которую хочешь увидеть в мире. — Махатма Ганди",
            "Счастье — это не что-то готовое. Оно приходит от ваших собственных действий. — Далай Лама",
            "На свете нет ничего более ценного, чем свободное время. — Даниил Гранин",
            "Только тот, кто движется вперед, может идти к успеху. — Лев Толстой",
            "Любовь — это когда счастье другого человека важнее твоего собственного. — Х. Джексон Браун",
            "Лучше быть одиноким, чем в плохой компании. — Джордж Вашингтон",
            "В жизни не бывает случайностей, все предопределено. — Григорий Перельман",
            "Секрет успеха в том, чтобы начать действовать. — Марк Твен",
            "Учиться — это как плыть против течения: если не идешь вперед, то откатишься назад. — Лао Цзы",
            "С новым днем приходит новая сила и новые мысли.  - Элеонора Рузвельт",
            "Жизнь заключается в том, чтобы оказывать влияние, а не получать доход.  - Кевин Круз",
            "Воля к победе, желание добиться успеха, стремление полностью раскрыть свой потенциал... вот ключи, которые откроют дверь к личному совершенству. - Конфуций",
            "Когда я отпускаю то, кем я являюсь, я становлюсь тем, кем я могу быть. - Лао-Цзы",
            "Реальная возможность добиться успеха кроется в самом человеке, а не в работе.  - Зиг Зиглар",
            "Давайте же буквально жить от мгновения к мгновению. - Махатма Ганди",
            "Не смотрите на часы; делайте то, что они делают. Не останавливайтесь. - Сэм Левенсон",
            "Вы никогда не сможете пересечь океан, пока не наберетесь смелости потерять из виду берег.  - Христофор Колумб",
            "Наша самая большая слабость заключается в том, что мы сдаемся. Самый верный путь к успеху - это попробовать еще раз. - Томас А. Эдисон",
            "Если возможность не стучиться к вам, постройте дверь. - Милтон Берл",
            "Будьте собой; все остальные уже заняты. - Оскар Уайльд",
            "Чтобы стать лучшим, нужно уметь справляться с худшим. - Уилсон Канади",
            "Каждый день делайте одно дело, которого вы боитесь. - Элеонора Рузвельт",
            "Умение жить - самая редкая вещь в мире. Большинство людей просто существует. - Оскар Уайльд",
            "Не бойся перемен. Ты можешь потерять что-то хорошее, но получишь что-то лучшее. — Джим Рон",
            "Кто ищет, тот всегда найдет. — Японская пословица",
            "Секрет счастья в том, чтобы уметь наслаждаться каждым моментом. — Джон Стюарт Милль",
            "Тот, кто хочет быть счастливым, должен стремиться быть добрым. — Леонардо да Винчи",
            "Не позволяй мелочам разрушить твоё счастье. — Марк Твен",
            "Успех — это когда ты можешь достичь своих целей и сохранить внутреннее спокойствие. — Наполеон Хилл",
            "Счастье — это не конечная цель, а способ путешествия. — Арнольд Палмер",
            "Мудрость приходит с опытом. — Вольтер",
            "Нет ничего невозможного для того, кто верит в себя. — Нельсон Мандела",
            "Мы — это то, что мы делаем постоянно. Поэтому совершенство — это не действие, а привычка. — Аристотель",
            "Самый простой способ начать — это перестать говорить и начать делать. — Уолт Дисней",
            "Только в темноте можно увидеть звезды. — Мартин Лютер Кинг",
            "Верь в себя и все станет возможным. — Кристиан Д. Ларсен",
            "Нельзя изменить направление ветра, но можно отрегулировать свои паруса. — Джим Рон",
            "Будущее принадлежит тем, кто верит в свои мечты. — Элеонор Рузвельт",
            "Жизнь — это не ожидание, пока буря пройдет, а умение танцевать под дождем. — Вивиан Грин",
            "Секрет успеха — это начать с того, что нужно сделать, а затем сделать то, что нужно. — Питер Друкер",
            "Не ждите. Время никогда не будет «подходящим». — Наполеон Хилл",
            "Если ты хочешь быть счастливым, будь им. — Лев Толстой",
            "Человек — это то, что он делает. — Махатма Ганди",
            "Каждый день — это новый шанс изменить свою жизнь. — Незвестен",
            "Секрет успеха в том, чтобы делать то, что любишь. — Майкл Джордан",
            "В жизни не бывает ничего случайного. Все имеет свой смысл. — Пауло Коэльо",
            "Отличие успешных людей от неуспешных в том, что успешные люди действуют. — Тони Роббинс",
            "Не бойся изменений, они могут привести к чему-то хорошему. — Стив Джобс",
            "Каждый момент — это новый шанс. — Джек Кэнфилд",
            "Стремись быть не успешным, а ценным. — Альберт Эйнштейн",
            "Лучше верить в чудо, чем в чудо не верить. — Далай Лама",
            "Не останавливайся на достигнутом. — Уинстон Черчилль",
            "Будь собой и не пытайся быть тем, кем ты не являешься. — Джудит Рич Харрис",
            "Чем больше ты отдаешь, тем больше получаешь. — Лао Цзы",
            "Счастье — это не что-то готовое, это результат твоих собственных действий. — Далай Лама",
            "Секрет успеха в том, чтобы начать действовать. — Марк Твен",
            "Живи так, чтобы твои мечты стали реальностью. — Уолт Дисней",
            "Будь изменой, которую ты хочешь видеть в мире. — Махатма Ганди",
            "Будущее зависит от того, что ты делаешь сегодня. — Махатма Ганди",
            "Тот, кто идет медленно, но уверенно, дойдет до своей цели. — Микеланджело",
            "Никогда не поздно стать тем, кем ты мог бы быть. — Джордж Элиот",
            "Не позволяй мелочам разрушать твоё счастье. — Марк Твен",
            "Человек, который никогда не ошибается, никогда не пробует ничего нового. — Альберт Эйнштейн",
            "Успех не окончателен, неудача не фатальна: важно лишь мужество продолжать. — Уинстон Черчилль",
            "Каждый день — это новый шанс изменить свою жизнь. — Неизвестен",
            "Самый простой способ начать — это перестать говорить и начать делать. — Уолт Дисней",
            "Секрет успешного бизнеса в том, чтобы постоянно улучшаться. — Генри Форд",
            "Верь в свои мечты и иди к ним. — Нельсон Мандела",
            "Жизнь — это не ожидание, пока буря пройдет, а умение танцевать под дождем. — Вивиан Грин",
            "Не бойся перемен, они могут привести к чему-то хорошему. — Стив Джобс",
            "Каждый момент — это новый шанс. — Джек Кэнфилд",
            "Секрет успеха в том, чтобы делать то, что любишь. — Майкл Джордан",
            "Будь собой, все остальные роли уже заняты. — Оскар Уайльд",
            "Секрет счастья в том, чтобы уметь наслаждаться каждым моментом. — Джон Стюарт Милль",
            "Успех — это когда ты можешь достичь своих целей и сохранить внутреннее спокойствие. — Наполеон Хилл",
            "Самый простой способ начать — это перестать говорить и начать делать. — Уолт Дисней",
            "Секрет счастья в том, чтобы уметь наслаждаться каждым моментом. — Джон Стюарт Милль",
            "Счастье — это не конечная цель, а способ путешествия. — Арнольд Палмер",
            "Секрет успеха в том, чтобы начать действовать. — Марк Твен",
            "Человек, который никогда не ошибается, никогда не пробует ничего нового. — Альберт Эйнштейн",
            "Секрет успешного бизнеса в том, чтобы постоянно улучшаться. — Генри Форд",
            "Успех не окончателен, неудача не фатальна: важно лишь мужество продолжать. — Уинстон Черчилль",
            "Человек — это то, что он делает. — Махатма Ганди",
            "Каждый день — это новый шанс изменить свою жизнь. — Неизвестен",
            "Тот, кто хочет быть счастливым, должен стремиться быть добрым. — Леонардо да Винчи",
            "Секрет успеха в том, чтобы делать то, что любишь. — Майкл Джордан",
            "Лучше верить в чудо, чем в чудо не верить. — Далай Лама",
            "Счастье — это не что-то готовое, это результат твоих собственных действий. — Далай Лама",
            "Человек, который никогда не ошибается, никогда не пробует ничего нового. — Альберт Эйнштейн",
            "Тот, кто идет медленно, но уверенно, дойдет до своей цели. — Микеланджело",
            "Не останавливайся на достигнутом. — Уинстон Черчилль",
            "Живи так, чтобы твои мечты стали реальностью. — Уолт Дисней",
            "Будущее зависит от того, что ты делаешь сегодня. — Махатма Ганди",
            "Каждый день — это новый шанс изменить свою жизнь. — Неизвестен",
            "Секрет счастья в том, чтобы уметь наслаждаться каждым моментом. — Джон Стюарт Милль",
            "Секрет успеха в том, чтобы начать действовать. — Марк Твен",
            "Счастье — это не конечная цель, а способ путешествия. — Арнольд Палмер",
            "Будь собой; все остальные роли уже заняты. — Оскар Уайльд",
            "Жизнь — это то, что происходит, пока мы строим планы. — Джон Леннон",
            "Не позволяй мелочам разрушить твоё счастье. — Марк Твен",
            "Не бойтесь отстаивать то, во что верите, даже если это означает стоять в одиночестве. - Энди Бирсак",
            "Ничего не меняя, ничего не изменится. - Тони Роббинс",
            "Я благодарен всем тем, кто сказал мне 'Нет'. Именно благодаря им я чего-то добился сам.  - Альберт Эйнштейн",
            "Люди часто говорят, что мотивация недолговечна. Что ж и с купанием также.  Вот почему мы рекомендуем принимать ее ежедневно. - Зиг Зиглар",
            "Есть только один способ избежать критики: ничего не делать, ничего не говорить и никем не быть. - Аристотель",
            "Если ветер не помогает, беритесь за весла. - Латинская пословица"
    };
    private Socket socket;
    private int num;

    public void setSocket(int num, Socket socket) {
        this.num = num;
        this.socket = socket;

        start();
    }

    public void run() {
        try {
            // Определяем входной и выходной потоки сокета
            // для обмена данными с клиентом
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String line;
            Random random = new Random();
            while (true) {
                line = dis.readUTF();  // Ожидание сообщения от клиента
                System.out.printf("Client '%d' response:\n\t", num); //Ответ со стороны клиента
                System.out.println(line);
                System.out.println("Sending back...");
                // Отсылаем клиенту обратно эту самую строку текста
                dos.writeUTF(quotes[random.nextInt(150)]);
                // Завершаем передачу данных
                dos.flush();
                System.out.println("Random quote has been sent");
                System.out.println();
                if (line.equalsIgnoreCase("No")) {
                    socket.close();
                    System.out.printf("Client '%d' closed connection", num);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ServerSocket srvSocket = null;  // Инициализация сокета сервера
        try {
            try {
                int i = 0;
                InetAddress ia = InetAddress.getByName("localhost");
                srvSocket = new ServerSocket(PORT, 0, ia); // Создание сокета сервера
                System.out.println("Server started\n\n");

                while (true) {
                    Socket socket = srvSocket.accept(); //Ожидание подключения
                    System.err.println("\n\nClient accepted"); //Выводим в консоль сообщение КРАСНОГО ЦВЕТА
                    //Стартуем обработку клиента в отдельном потоке
                    new Server().setSocket(i++, socket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (srvSocket != null) {
                    srvSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);

    }
}
