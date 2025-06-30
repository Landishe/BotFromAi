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
<<<<<<< HEAD

=======
            
>>>>>>> 09590705c160d4cc46d143eb03f14c11f4082fe7
    state: RemeberCity
        script:
            $client.city = $request.query
            $session.cart = []
        go!:/ChoosePizza
<<<<<<< HEAD

=======
        
>>>>>>> 09590705c160d4cc46d143eb03f14c11f4082fe7
    state: ClickButtons
        q: *
        a: Нажмите, пожалуйста, кнопку
        go!: ..
        
    state: CatchAll || noContext = true
        event!: noMatch
        a: Я вас не понимаю
     