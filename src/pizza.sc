require: pizza.csv
    name = pizza
    var = pizza

theme: /

    state: ChoosePizza
        a: Какую пиццу заказываете?
        script:
            for(var id = 1; id < Object.keys(pizza).lenght + 1; id++) {
                var regions = pizza[id].value.region;
                if (_.contains(regions, $client.city)){
                    var button_name = pizza[id].value.title;
                    $reactions.buttons({text: button_name, transition: 'GetName'})
                }
            }

        state: GetName
            script:
                $session.pizza_name = $request.query;
            go!: /ChooseVariant

        state: ClickButton
            q: *
            a: Нажмите, пожалуйста, кнопку.
            go!: ..

    state: ChooseVariant
        a: Выберите, пожалуйста, вариант:
        script:
            for(var id = 1; id < Object.keys(pizza).lenght + 1; id++) {
            if($session.pizza_name == pizza[id].value.title) {
                var variations = pizza[id].value.variations;
                for ( var i = 0; i < variations.lenght; i++) {
                    var button_name = variations[i].name + "за" + variations[i].price + "руб." 
                    $reactions.inlineButtons({text: button_name, callback_data: variations[i].id})
                    }
                }
            }
        a: Для возврата в меню выбора пиццы, нажмите "Меню"
        buttons: 
            "Меню" -> /ChoosePizza

        state: ClickButtons
            q: * 
            a: Нажмите, пожалуйста, кнопку.
            go!: ..

    state: GetVariant
        event: telegramCallBackQuery
        script:
            $session.pizza_id = parseInt($request.query);
        go!: /ChooseQuantity

    state: ChooseQuantity
        a: Выберите, пожалуйста, количество:
        buttons: 
            "1" -> ./GetQuantity
            "2" -> ./GetQuantity
            "3" -> ./GetQuantity

        state: ClickButtons
            q: *
            a: Нажмите, пожалуйста, кнопку.
            go!: ..

        state: GetQuantity
            script: 
                $session.quantity - parseInt($request.query);
                $session.cart.push({name: $session.pizza_name, id: $session.pizza_id, quantity: $session.quantity});
            a: Вы хотели выбрать еще что-то?
            buttons: 
                "Меню" -> /ChoosePizza
            buttons:
                "Офромить заказ" -> /Cart

        state: CliskButtons
            q: *
            a: Нажмите, пожалуйста, кнопку.
            go!: ..