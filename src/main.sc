require: pizza.sc

theme: /

    state: Start
        q!: $regex</start>
        script:
            $context.session = {}
            $context.client = {}
            $context.temp = {}
            $context.respons = {}
        a: Привет! Я электронный помощник. Помогу вам заказать пиццу!
        go!: /ChooseCity
        
    state: ChooseCity || modal = true
        a: Выбирете город
        buttons: 
            "Санкт-Петербург" -> ./RememberCity
            "Москва" -> ./RememberCity
            
        state: RemeberCity
            script:
                $client.city = $request.query;
                $session.cart = [];
            go!: ChoosePizza
            
        state: ClickButtons
            q: *
            a: Нажмите, пожалуйста, кнопку
            go!: ..
        
    state: CatchAll || noContext=true
        event!: noMatch
        a: Я вас не понимаю
        go!: /Start