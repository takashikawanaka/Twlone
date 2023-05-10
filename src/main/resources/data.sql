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
        );
