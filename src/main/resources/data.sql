INSERT
    INTO
        USER(user_id, name, description, icon, delete_flag, created_at)
    VALUES
        (
            "test"
            ,"test"
            ,"many test"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
        USER(user_id, name, description, icon, delete_flag, created_at)
    VALUES
        (
            'taro_yamada'
            ,'山田太郎'
            ,"I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user,I'm dammy user"
            ,ELT(
                CEIL(RAND() * 6)
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
                ,"default/1.png"
                ,"default/2.png"
                ,"default/3.png"
                ,"default/4.png"
                ,"default/5.png"
                ,"default/6.png"
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
            ,CURRENT_TIMESTAMP
        )
        ,(
            '明日の天気は晴れだって。散歩に行こうかな。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '仕事が終わったら、ジムに行く予定。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '最近、読んでいる本が面白い。おすすめだよ。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '昨日の夜、星空が綺麗だった。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            'テレビで面白い番組を見つけた。毎週楽しみ！'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '友達とカフェでおしゃべりしてきた。楽しかった！'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
        )
        ,(
            '今週末は家族でピクニックに行く予定。'
            ,3
            ,0
            ,CURRENT_TIMESTAMP
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
            ""
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
        );
