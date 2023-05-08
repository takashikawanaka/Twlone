INSERT
    INTO
        USER(user_id, name, description, icon, back, delete_flag, created_at)
    VALUES
        (
            "test"
            ,"test"
            ,"many test"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            "dev"
            ,"dev"
            ,"developing log"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        authorization(
            user_id
            ,password
            ,created_at
            ,updated_at
        )
    VALUES
        (
            1
            ,"$2a$08$clh9XaYYznpX9WDqySgiCuUu4znpSeu2oJi5l2Q00UJs42Llrbd7S"
            ,CURRENT_TIMESTAMP
            ,CURRENT_TIMESTAMP
        )
        ,(
            2
            ,"$2a$08$clh9XaYYznpX9WDqySgiCuUu4znpSeu2oJi5l2Q00UJs42Llrbd7S"
            ,CURRENT_TIMESTAMP
            ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        USER(user_id, name, description, icon, back, delete_flag, created_at)
    VALUES
        (
            'taro_yamada'
            ,'山田太郎'
            ,"I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'emily_smith'
            ,'Emily Smith'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'hanako_suzuki'
            ,'鈴木花子'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'john_doe'
            ,'John Doe'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'saburo_watanabe'
            ,'渡辺三郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'jane_doe'
            ,'Jane Doe'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'shiro_ito'
            ,'伊藤四郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'bob_smith'
            ,'Bob Smith'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'shichiro_saito'
            ,'斉藤七郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'susan_johnson'
            ,'Susan Johnson'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'jiro_tanaka'
            ,'田中二郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'michael_brown'
            ,'Michael Brown'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'goro_kobayashi'
            ,'小林五郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'sarah_williams'
            ,'Sarah Williams'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'rokuro_kato'
            ,'加藤六郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'david_jones'
            ,'David Jones'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'hachiro_kimura'
            ,'木村八郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'elizabeth_taylor'
            ,'Elizabeth Taylor'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'ichiro_sato'
            ,'佐藤一郎'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'james_davis'
            ,'James Davis'
            ,"I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
    VALUES
        (
            '今日のランチはラーメンだった。美味しかった！'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 1 DAY
            )
        )
        ,(
            '明日の天気は晴れだって。散歩に行こうかな。'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 21 HOUR
            )
        )
        ,(
            '仕事が終わったら、ジムに行く予定。'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 18 HOUR
            )
        )
        ,(
            '最近、読んでいる本が面白い。おすすめだよ。'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 15 HOUR
            )
        )
        ,(
            '昨日の夜、星空が綺麗だった。'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 12 HOUR
            )
        )
        ,(
            'テレビで面白い番組を見つけた。毎週楽しみ！'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 9 HOUR
            )
        )
        ,(
            '友達とカフェでおしゃべりしてきた。楽しかった！'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 6 HOUR
            )
        )
        ,(
            '今週末は家族でピクニックに行く予定。'
            ,3
            ,0
            ,DATE_SUB(
                CURRENT_TIMESTAMP
                ,INTERVAL 3 HOUR
            )
        )
        ,(
            '新しい趣味として、ギターを始めようかな。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '最近、健康のために野菜をたくさん食べている。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
    VALUES
        (
            'Just had the best cup of coffee ☕️ #coffeeaddict'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Can''t believe it''s already Monday again 😩 #mondayblues'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Beautiful day for a hike 🌞🌲 #naturelover'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'So excited for the concert tonight 🎶 #musicislife'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Just finished a great book 📚 #bookworm'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Pizza for dinner 🍕 #yum'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Rainy days call for cozy blankets and movies 🌧️🎥 #lazyday'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Workout done 💪 #fitnessmotivation'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Weekend getaway with friends 🚗🌊 #beachlife'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'Love this city ❤️🏙️#citylife'
            ,4
            ,0
            ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
    VALUES
        (
            '今日の天気は最高です！'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '昨日の夕食はとても美味しかったです。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '今週末に友達と旅行に行く予定です。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '最近、新しい趣味を見つけました。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '仕事が忙しくて、疲れました。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '明日は久しぶりに家族と会います。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'ペットの犬が可愛すぎます！'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '今日のランチはサンドイッチでした。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '新しい映画を見に行きたいです。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '来週の予定が楽しみです。'
            ,5
            ,0
            ,CURRENT_TIMESTAMP
        );
-- Follow
INSERT
    INTO
        Follow(
            user_id
            ,target_user_id
        )
    VALUES
        (
            1
            ,2
        )
        ,(
            2
            ,1
        );
-- Favorite
INSERT
    INTO
        Favorite(
            tw_id
            ,user_id
        )
    VALUES
        (
            1
            ,1
        );
-- ReTw && ReTw with Content
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,re_tw_id
            ,delete_flag
            ,created_at
        )
    VALUES
        (
            ''
            ,1
            ,1
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            "Good"
            ,1
            ,1
            ,0
            ,CURRENT_TIMESTAMP
        );
-- Media
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
    VALUES
        (
            "Media"
            ,1
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            "Media2"
            ,1
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            "Media3"
            ,1
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            "Media4"
            ,1
            ,0
            ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Media(
            tw_id
            ,type
            ,path
        )
        VALUES
        (
            33
            ,'jpeg'
            ,"test.jpeg"
        ),  (
            34
            ,'jpeg'
            ,"test2.jpg"
        ),  (
            34
            ,'jpeg'
            ,"test3.jpg"
        ),  (
            35
            ,'jpeg'
            ,"test4.jpg"
        ),(
            35
            ,'jpeg'
            ,"test5.jpg"
        ),(
            35
            ,'jpeg'
            ,"test6.jpg"
        ),(
            36
            ,'jpeg'
            ,"test7.jpeg"
        ),(
            36
            ,'jpeg'
            ,"test8.jpg"
        ),(
            36
            ,'jpeg'
            ,"test9.jpg"
        ),(
            36
            ,'jpeg'
            ,"test10.jpg"
        );
-- ReTw with Media
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,re_tw_id
            ,delete_flag
            ,created_at
        )
    VALUES
    (
            ''
            ,1
            ,33
            ,0
            ,CURRENT_TIMESTAMP
        ),
        (
            "Media1 ReTw"
            ,1
            ,33
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            "Media2 ReTw"
            ,1
            ,34
            ,0
            ,CURRENT_TIMESTAMP
        ),(
            "Media3 ReTw"
            ,1
            ,35
            ,0
            ,CURRENT_TIMESTAMP
        ),(
            "Media4 ReTw"
            ,1
            ,36
            ,0
            ,CURRENT_TIMESTAMP
        );
--Tw && Reply
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
        VALUES
        (
        "Long Text\nLong TextLong Text\nLong TextLong TextLong Text\nLong TextLong TextLong TextLong Text\nLong TextLong TextLong TextLong TextLong Text"
        , 1
        , 0
        ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,reply_tw_id
            ,delete_flag
            ,created_at
        )
        VALUES (
        "@taro_yamada Test Reply"
        , 1
        , 1
        , 0
        ,CURRENT_TIMESTAMP
        );
--Long Image
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
        VALUES (
        "Long Image"
        , 1
        , 0
        ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Media(
            tw_id
            ,type
            ,path
        )
        VALUES
        (
            44
            ,'png'
            ,"test11.png"
        ),(
            44
            ,'png'
            ,"test12.png"
        ),(
            44
            ,'png'
            ,"test13.png"
        ),(
            44
            ,'png'
            ,"test14.png"
        );
-- Long ReplyTw
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
        VALUES (
        "ChatGPTは、オープンソースの自然言語処理ツールであるOpenAI GPT-2をベースにしたチャットボットです。GPT-2は、大規模なテキストコーパスから学習したニューラルネットワークであり、任意のテキストに続く文を生成することができます。ChatGPTは、GPT-2をチャットボットに適用するために、以下のような工夫をしています。"
        , 1
        , 0
        ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,reply_tw_id
            ,delete_flag
            ,created_at
        )
        VALUES (
        "@test\n- チャットボットの対話データセットからファインチューニングすることで、対話的な文体や応答性を向上させています。\n- ユーザーの発話に対して、複数の候補文を生成し、それらをランキングすることで、最も適切な応答を選択しています。\n- 生成された文に対して、品質や安全性を評価するフィルターをかけることで、不適切な内容や誤りを排除しています。\n- ユーザーの属性や興味に応じて、パーソナリティやトピックを調整することで、個性的で魅力的な会話を実現しています。"
        , 1
        , 45
        , 0
        ,CURRENT_TIMESTAMP
        ), (
        "@test\nChatGPTは、自然言語処理の最先端技術を用いて、人間らしい会話を生成することができるチャットボットです。しかし、まだ完璧ではありません。ChatGPTは、学習したテキストコーパスに依存しており、その中に含まれるバイアスや偏見を反映する可能性があります。また、常識や論理性に欠ける文や、事実と異なる文を生成することもあります。そのため、ChatGPTと会話する際には、その限界や課題を理解し、適切な距離感を持って楽しむことが重要です。"
        , 1
        , 46
        , 0
        ,CURRENT_TIMESTAMP
        );
-- HashTag Tw
INSERT
    INTO
        HashTag(
            name
        )
        VALUES (
        "test"
        ),(
        "HelloWorld"
        );
INSERT
    INTO
        Tw(
            content
            ,user_id
            ,delete_flag
            ,created_at
        )
        VALUES (
        "hashtag test\n#test"
        , 1
        , 0
        ,CURRENT_TIMESTAMP
        );
INSERT
    INTO
        Related_Tw_Hashtag(
            tw_id
            ,hashtag_id
        )
        VALUES (
        48
        , 1
        );
-- Dammy
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('はじめまして #HelloWorld',7,0,CURRENT_TIMESTAMP);
INSERT INTO Related_Tw_Hashtag(tw_id,hashtag_id) VALUES (49,2);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日の仕事は忙しかった。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今週末はゴルフに行く予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日の会議に備えないと。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日のランチは美味しかった。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日は早起きしてジムに行こう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日の通勤電車は混んでいた。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今晩は家でゆっくりしよう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日の天気はどうだろう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今週末は家族でお出かけする予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('最近、仕事が忙しくて疲れている。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日のプレゼンに備えないと。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日の夕飯は何にしようかな。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今週末は友達と飲みに行く予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('最近、仕事が上手くいかない。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日は早起きしてランニングに行こう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日の通勤電車は空いていた。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今晩は外食しようかな。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日の天気予報を確認しないと。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今週末は家族でBBQする予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('最近、ストレスが溜まっている。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日の仕事は楽しかった。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今週末は釣りに行く予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日のミーティングに備えないと。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日のディナーは美味しかった。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日は早起きしてヨガに行こう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日のバス通勤は混んでいた。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今晩は映画を見ようかな。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明後日の天気はどうだろう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('来週末は家族でキャンプする予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('最近、仕事が充実している。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日のデッドラインに間に合わせないと。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今晩は何を食べようかな。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('来週末は友達と旅行する予定。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('最近、仕事がスムーズに進んでいる。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('明日は早起きしてサイクリングに行こう。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今日の車通勤は渋滞していた。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('今晩は読書でもしようかな。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('来週の天気予報を確認しないと。',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('来月末に家族旅行する予定だけど、準備しないと',7,0,CURRENT_TIMESTAMP);
INSERT INTO Tw(content,user_id,delete_flag,created_at) VALUES ('最近、仕事が楽しい。',7,0,CURRENT_TIMESTAMP);


INSERT
    INTO
        USER(user_id, name, description, icon, back, delete_flag, created_at)
    VALUES
        (
            'timer_bot'
            ,'時報BOT'
            ,""
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.png"
                ,"default_2.png"
                ,"default_3.png"
                ,"default_4.png"
                ,"default_5.png"
                ,"default_6.png"
            )
            ,ELT(
                CEIL(RAND() * 6)
                ,"default_1.jpg"
                ,"default_2.jpg"
                ,"default_3.jpg"
                ,"default_4.jpg"
                ,"default_5.jpg"
                ,"default_6.jpg"
            )
            ,0
            ,CURRENT_TIMESTAMP
        )