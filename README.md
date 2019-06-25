## requirements

Исследование AccountService'а
------------------------------------------------------------------------------------------------------
Цель: создать сервис и оценить время доступа к нему в зависимости от входных параметров
------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------
1. Создать сервис со следующим интерфейсом:
------------------------------------------------------------------------------------------------------

public interface AccountService
{
    /**
     * Retrieves current balance or zero if addAmount() method was not called before for specified id
     *
     * @param id balance identifier
     */
    Long getAmount(Integer id);

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id balance identifier
     * @param value positive or negative value, which must be added to current balance
     */
    void addAmount(Integer id, Long value);
}

Сервис будет работать в высоконагруженной отказоустойчивой системе.
Сервис должен кэшировать данные в памяти и сохранять данные в БД (Oracle, PostgreSQL, MySQL)
или бросать Exception'ы если выполнить операцию не удалось.
В качестве транспортного слоя можно выбрать любой из протоколов RMI, Hessian, HTTP

------------------------------------------------------------------------------------------------------
2. Создать тестового клиента
------------------------------------------------------------------------------------------------------

Тестовый клиент должен уметь запускать несколько конкурентных потоков на определённом подмножестве идентификаторов
  - rCount - количество читателей вызывающих метод getAmount(id)
  - wCount - количество читателей вызывающих метод addAmount(id,value)
  - idList - список или доапазон ключей которые будут использоваться для тестирования
Эти параметры можно задавать через командную строчку или конфигурационный файл.
Одновременно можно запускать несколько тестовых клиентов на одном или разных компьютерах.

------------------------------------------------------------------------------------------------------
3. Получить стаистику обрабатки запросов на сервере AccountService'ом
------------------------------------------------------------------------------------------------------

Для каждого из двух методов AccountService'а (getAmount, addAmount) нужно получить
  - кол-во запросов обрабатываемых в единицу времени на сервере (!!! не на клиенте)
  - общее кол-во запросов от всех клентов

Статистику с сервиса можно получать по требованию любым способом
или сбрасывать в лог с определённой периодичностью.
Предусмотреть возможность сбросить статистику в ноль на работающем сервисе.

## decision: accountService

used for calculate(sum) some amounts by id(person, organization)
    
values are saved in db and kept in local cache

application.properties manages db recreation and logging level

technologies

    spring-boot
    spring-mvc
    hibernate

has rest endpoints

    GET http://localhost:8080/account/{id}
    PUT http://localhost:8080/account/{id}/{amount}
    GET http://localhost:8080/reset-statistic
    GET http://localhost:8080/get-statistic
    
statistic
    
    read:73 write:419
    readTotal:2754 writeTotal:10046

next database expected for service

    jdbc:postgresql://localhost:5432/postgres?currentSchema=account 
    schema/login/password=account

main class for run service
    
    ru.iportnyagin.accountservice.Service


## test client

there is client for test accountService.
client instances are run in concurrent threads

main class for run client 

    ru.iportnyagin.client.Client

args for client
    
    host_with_port readCount writeCount id1,id2,id3...
    
example

    http://localhost:8080 50 50 1,2,3,4,5,6,7,8,9,10,11,30
