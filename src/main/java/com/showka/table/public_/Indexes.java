/*
 * This file is generated by jOOQ.
*/
package com.showka.table.public_;


import com.showka.table.public_.tables.M_BUSHO;
import com.showka.table.public_.tables.M_BUSHO_DATE;
import com.showka.table.public_.tables.M_KOKYAKU;
import com.showka.table.public_.tables.M_NYUKIN_KAKE_INFO;
import com.showka.table.public_.tables.M_SHAIN;
import com.showka.table.public_.tables.M_SHOHIN;
import com.showka.table.public_.tables.M_USER;
import com.showka.table.public_.tables.R_URIAGE;
import com.showka.table.public_.tables.R_URIAGE_KEIJO;
import com.showka.table.public_.tables.R_URIAGE_KEIJO_TEISEI;
import com.showka.table.public_.tables.R_URIAGE_MEISAI;
import com.showka.table.public_.tables.T_SEIKYU;
import com.showka.table.public_.tables.T_SEIKYU_MEISAI;
import com.showka.table.public_.tables.T_SHOHIN_IDO;
import com.showka.table.public_.tables.T_SHOHIN_IDO_MEISAI;
import com.showka.table.public_.tables.T_SHOHIN_ZAIKO;
import com.showka.table.public_.tables.T_URIAGE;
import com.showka.table.public_.tables.T_URIAGE_MEISAI;
import com.showka.table.public_.tables.T_URIKAKE;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index PRIMARY_KEY_8 = Indexes0.PRIMARY_KEY_8;
    public static final Index UK_5BY1GUP4TXK8FK16KGF8MPF38_INDEX_8 = Indexes0.UK_5BY1GUP4TXK8FK16KGF8MPF38_INDEX_8;
    public static final Index PRIMARY_KEY_E = Indexes0.PRIMARY_KEY_E;
    public static final Index UK_KGBIV56WTAT03W6I4V383FU7A_INDEX_E = Indexes0.UK_KGBIV56WTAT03W6I4V383FU7A_INDEX_E;
    public static final Index FKM9UAXXH78HO91ET19MDDUBHGL_INDEX_2 = Indexes0.FKM9UAXXH78HO91ET19MDDUBHGL_INDEX_2;
    public static final Index PRIMARY_KEY_2F = Indexes0.PRIMARY_KEY_2F;
    public static final Index UK_7UBB14KG5P5MBCYPBMRVYR7HO_INDEX_2 = Indexes0.UK_7UBB14KG5P5MBCYPBMRVYR7HO_INDEX_2;
    public static final Index PRIMARY_KEY_E7 = Indexes0.PRIMARY_KEY_E7;
    public static final Index UK_L40OVD3L702092P9XG88XU4VX_INDEX_E = Indexes0.UK_L40OVD3L702092P9XG88XU4VX_INDEX_E;
    public static final Index FK6EXWC57AN76H9BS794TJGTUGN_INDEX_9 = Indexes0.FK6EXWC57AN76H9BS794TJGTUGN_INDEX_9;
    public static final Index PRIMARY_KEY_9 = Indexes0.PRIMARY_KEY_9;
    public static final Index UK_81PKBX9M7PBAFUMTCUKHTOOBH_INDEX_9 = Indexes0.UK_81PKBX9M7PBAFUMTCUKHTOOBH_INDEX_9;
    public static final Index PRIMARY_KEY_7 = Indexes0.PRIMARY_KEY_7;
    public static final Index UK_QQ0UP8I0X3HO6U8CL0QEMQ4QJ_INDEX_7 = Indexes0.UK_QQ0UP8I0X3HO6U8CL0QEMQ4QJ_INDEX_7;
    public static final Index PRIMARY_KEY_88 = Indexes0.PRIMARY_KEY_88;
    public static final Index UK_BRMB54LD0GQSTOGMB812XW951_INDEX_8 = Indexes0.UK_BRMB54LD0GQSTOGMB812XW951_INDEX_8;
    public static final Index UK_E0AD43H00N5AJYKDHEM6ODJ79_INDEX_8 = Indexes0.UK_E0AD43H00N5AJYKDHEM6ODJ79_INDEX_8;
    public static final Index FKPA4RJI1OV770T37MNYHLVHTY1_INDEX_7 = Indexes0.FKPA4RJI1OV770T37MNYHLVHTY1_INDEX_7;
    public static final Index PRIMARY_KEY_7D = Indexes0.PRIMARY_KEY_7D;
    public static final Index UK_3PUXJQQMYDE93SCH3O89I3UV7_INDEX_7 = Indexes0.UK_3PUXJQQMYDE93SCH3O89I3UV7_INDEX_7;
    public static final Index FK88YWWSH8XTTSGK28INR0I5POH_INDEX_2 = Indexes0.FK88YWWSH8XTTSGK28INR0I5POH_INDEX_2;
    public static final Index PRIMARY_KEY_21 = Indexes0.PRIMARY_KEY_21;
    public static final Index UK_68YGMNT83I8TJ9VCTJUJ7DNOH_INDEX_2 = Indexes0.UK_68YGMNT83I8TJ9VCTJUJ7DNOH_INDEX_2;
    public static final Index FKJ7YBAXR6IN6VUA1KLM2I10YEK_INDEX_F = Indexes0.FKJ7YBAXR6IN6VUA1KLM2I10YEK_INDEX_F;
    public static final Index PRIMARY_KEY_F = Indexes0.PRIMARY_KEY_F;
    public static final Index UK_5FQTU9VF1ITABDUFF88E46ALY_INDEX_F = Indexes0.UK_5FQTU9VF1ITABDUFF88E46ALY_INDEX_F;
    public static final Index FKBIIJRTBUHPP07F2W0VMMA3P8F_INDEX_5 = Indexes0.FKBIIJRTBUHPP07F2W0VMMA3P8F_INDEX_5;
    public static final Index FKMGXRSBS5HMJ32AOXEMX6UROQR_INDEX_5 = Indexes0.FKMGXRSBS5HMJ32AOXEMX6UROQR_INDEX_5;
    public static final Index PRIMARY_KEY_5 = Indexes0.PRIMARY_KEY_5;
    public static final Index UK_DHV8J2AK6U0FRWNAWO7MHUDEP_INDEX_5 = Indexes0.UK_DHV8J2AK6U0FRWNAWO7MHUDEP_INDEX_5;
    public static final Index PRIMARY_KEY_4 = Indexes0.PRIMARY_KEY_4;
    public static final Index UK_4SCPAF4QMJRAM1VXMKUYJ269J_INDEX_4 = Indexes0.UK_4SCPAF4QMJRAM1VXMKUYJ269J_INDEX_4;
    public static final Index FK9AXJO7K5P73YYEWXSIC6HT04W_INDEX_E = Indexes0.FK9AXJO7K5P73YYEWXSIC6HT04W_INDEX_E;
    public static final Index PRIMARY_KEY_ED = Indexes0.PRIMARY_KEY_ED;
    public static final Index UK_R4P4GM00P10V2HXDQSBCNVPP0_INDEX_E = Indexes0.UK_R4P4GM00P10V2HXDQSBCNVPP0_INDEX_E;
    public static final Index FKK5EMLYFTBF5PBADDC4QFNMGLH_INDEX_1 = Indexes0.FKK5EMLYFTBF5PBADDC4QFNMGLH_INDEX_1;
    public static final Index PRIMARY_KEY_1 = Indexes0.PRIMARY_KEY_1;
    public static final Index UK_9HUM7RYAHI4JAIGE2V6TD9M13_INDEX_1 = Indexes0.UK_9HUM7RYAHI4JAIGE2V6TD9M13_INDEX_1;
    public static final Index FKNYESTD5U0GAEDM2AUNTF943D1_INDEX_2 = Indexes0.FKNYESTD5U0GAEDM2AUNTF943D1_INDEX_2;
    public static final Index FKP0LRETW08695Q1FOIS2EK6Q6B_INDEX_2 = Indexes0.FKP0LRETW08695Q1FOIS2EK6Q6B_INDEX_2;
    public static final Index PRIMARY_KEY_25 = Indexes0.PRIMARY_KEY_25;
    public static final Index UK_QMKOJB8N58COAAOK60IQVBJ5B_INDEX_2 = Indexes0.UK_QMKOJB8N58COAAOK60IQVBJ5B_INDEX_2;
    public static final Index FKTKU4TUGX74WKRAT36T4WWNWEB_INDEX_7 = Indexes0.FKTKU4TUGX74WKRAT36T4WWNWEB_INDEX_7;
    public static final Index PRIMARY_KEY_78 = Indexes0.PRIMARY_KEY_78;
    public static final Index UK_97I9YEJ178G3KQCKRFJ4GJH0N_INDEX_7 = Indexes0.UK_97I9YEJ178G3KQCKRFJ4GJH0N_INDEX_7;
    public static final Index FK12V4JQQCK1CDREC88PF37AU7D_INDEX_4 = Indexes0.FK12V4JQQCK1CDREC88PF37AU7D_INDEX_4;
    public static final Index PRIMARY_KEY_4D = Indexes0.PRIMARY_KEY_4D;
    public static final Index UK_9F7L0VVJA040FK4UIR58IBQUN_INDEX_4 = Indexes0.UK_9F7L0VVJA040FK4UIR58IBQUN_INDEX_4;
    public static final Index FKEFJRU4SE98KKBOBDKHDJC8IIM_INDEX_B = Indexes0.FKEFJRU4SE98KKBOBDKHDJC8IIM_INDEX_B;
    public static final Index FKR12W6PQB2GNMH56OFRHY0LOSC_INDEX_B = Indexes0.FKR12W6PQB2GNMH56OFRHY0LOSC_INDEX_B;
    public static final Index PRIMARY_KEY_B = Indexes0.PRIMARY_KEY_B;
    public static final Index UK_QRBW4ETEB9S2JK3SPMEXAULIG_INDEX_B = Indexes0.UK_QRBW4ETEB9S2JK3SPMEXAULIG_INDEX_B;
    public static final Index PRIMARY_KEY_53 = Indexes0.PRIMARY_KEY_53;
    public static final Index UK_3RCPSMXXRKKRO42FWTDIPIAXB_INDEX_5 = Indexes0.UK_3RCPSMXXRKKRO42FWTDIPIAXB_INDEX_5;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index PRIMARY_KEY_8 = Internal.createIndex("PRIMARY_KEY_8", M_BUSHO.m_busho, new OrderField[] { M_BUSHO.m_busho.CODE }, true);
        public static Index UK_5BY1GUP4TXK8FK16KGF8MPF38_INDEX_8 = Internal.createIndex("UK_5BY1GUP4TXK8FK16KGF8MPF38_INDEX_8", M_BUSHO.m_busho, new OrderField[] { M_BUSHO.m_busho.RECORD_ID }, true);
        public static Index PRIMARY_KEY_E = Internal.createIndex("PRIMARY_KEY_E", M_BUSHO_DATE.m_busho_date, new OrderField[] { M_BUSHO_DATE.m_busho_date.BUSHO_ID }, true);
        public static Index UK_KGBIV56WTAT03W6I4V383FU7A_INDEX_E = Internal.createIndex("UK_KGBIV56WTAT03W6I4V383FU7A_INDEX_E", M_BUSHO_DATE.m_busho_date, new OrderField[] { M_BUSHO_DATE.m_busho_date.RECORD_ID }, true);
        public static Index FKM9UAXXH78HO91ET19MDDUBHGL_INDEX_2 = Internal.createIndex("FKM9UAXXH78HO91ET19MDDUBHGL_INDEX_2", M_KOKYAKU.m_kokyaku, new OrderField[] { M_KOKYAKU.m_kokyaku.SHUKAN_BUSHO_ID }, false);
        public static Index PRIMARY_KEY_2F = Internal.createIndex("PRIMARY_KEY_2F", M_KOKYAKU.m_kokyaku, new OrderField[] { M_KOKYAKU.m_kokyaku.CODE }, true);
        public static Index UK_7UBB14KG5P5MBCYPBMRVYR7HO_INDEX_2 = Internal.createIndex("UK_7UBB14KG5P5MBCYPBMRVYR7HO_INDEX_2", M_KOKYAKU.m_kokyaku, new OrderField[] { M_KOKYAKU.m_kokyaku.RECORD_ID }, true);
        public static Index PRIMARY_KEY_E7 = Internal.createIndex("PRIMARY_KEY_E7", M_NYUKIN_KAKE_INFO.m_nyukin_kake_info, new OrderField[] { M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.KOKYAKU_ID }, true);
        public static Index UK_L40OVD3L702092P9XG88XU4VX_INDEX_E = Internal.createIndex("UK_L40OVD3L702092P9XG88XU4VX_INDEX_E", M_NYUKIN_KAKE_INFO.m_nyukin_kake_info, new OrderField[] { M_NYUKIN_KAKE_INFO.m_nyukin_kake_info.RECORD_ID }, true);
        public static Index FK6EXWC57AN76H9BS794TJGTUGN_INDEX_9 = Internal.createIndex("FK6EXWC57AN76H9BS794TJGTUGN_INDEX_9", M_SHAIN.m_shain, new OrderField[] { M_SHAIN.m_shain.SHOZOKU_BUSHO_ID }, false);
        public static Index PRIMARY_KEY_9 = Internal.createIndex("PRIMARY_KEY_9", M_SHAIN.m_shain, new OrderField[] { M_SHAIN.m_shain.CODE }, true);
        public static Index UK_81PKBX9M7PBAFUMTCUKHTOOBH_INDEX_9 = Internal.createIndex("UK_81PKBX9M7PBAFUMTCUKHTOOBH_INDEX_9", M_SHAIN.m_shain, new OrderField[] { M_SHAIN.m_shain.RECORD_ID }, true);
        public static Index PRIMARY_KEY_7 = Internal.createIndex("PRIMARY_KEY_7", M_SHOHIN.m_shohin, new OrderField[] { M_SHOHIN.m_shohin.CODE }, true);
        public static Index UK_QQ0UP8I0X3HO6U8CL0QEMQ4QJ_INDEX_7 = Internal.createIndex("UK_QQ0UP8I0X3HO6U8CL0QEMQ4QJ_INDEX_7", M_SHOHIN.m_shohin, new OrderField[] { M_SHOHIN.m_shohin.RECORD_ID }, true);
        public static Index PRIMARY_KEY_88 = Internal.createIndex("PRIMARY_KEY_88", M_USER.m_user, new OrderField[] { M_USER.m_user.ID }, true);
        public static Index UK_BRMB54LD0GQSTOGMB812XW951_INDEX_8 = Internal.createIndex("UK_BRMB54LD0GQSTOGMB812XW951_INDEX_8", M_USER.m_user, new OrderField[] { M_USER.m_user.USERNAME }, true);
        public static Index UK_E0AD43H00N5AJYKDHEM6ODJ79_INDEX_8 = Internal.createIndex("UK_E0AD43H00N5AJYKDHEM6ODJ79_INDEX_8", M_USER.m_user, new OrderField[] { M_USER.m_user.RECORD_ID }, true);
        public static Index FKPA4RJI1OV770T37MNYHLVHTY1_INDEX_7 = Internal.createIndex("FKPA4RJI1OV770T37MNYHLVHTY1_INDEX_7", R_URIAGE.r_uriage, new OrderField[] { R_URIAGE.r_uriage.URIAGE_ID }, false);
        public static Index PRIMARY_KEY_7D = Internal.createIndex("PRIMARY_KEY_7D", R_URIAGE.r_uriage, new OrderField[] { R_URIAGE.r_uriage.KEIJO_DATE, R_URIAGE.r_uriage.URIAGE_ID }, true);
        public static Index UK_3PUXJQQMYDE93SCH3O89I3UV7_INDEX_7 = Internal.createIndex("UK_3PUXJQQMYDE93SCH3O89I3UV7_INDEX_7", R_URIAGE.r_uriage, new OrderField[] { R_URIAGE.r_uriage.RECORD_ID }, true);
        public static Index FK88YWWSH8XTTSGK28INR0I5POH_INDEX_2 = Internal.createIndex("FK88YWWSH8XTTSGK28INR0I5POH_INDEX_2", R_URIAGE_KEIJO.r_uriage_keijo, new OrderField[] { R_URIAGE_KEIJO.r_uriage_keijo.BUSHO_ID }, false);
        public static Index PRIMARY_KEY_21 = Internal.createIndex("PRIMARY_KEY_21", R_URIAGE_KEIJO.r_uriage_keijo, new OrderField[] { R_URIAGE_KEIJO.r_uriage_keijo.URIAGE_RIREKI_ID }, true);
        public static Index UK_68YGMNT83I8TJ9VCTJUJ7DNOH_INDEX_2 = Internal.createIndex("UK_68YGMNT83I8TJ9VCTJUJ7DNOH_INDEX_2", R_URIAGE_KEIJO.r_uriage_keijo, new OrderField[] { R_URIAGE_KEIJO.r_uriage_keijo.RECORD_ID }, true);
        public static Index FKJ7YBAXR6IN6VUA1KLM2I10YEK_INDEX_F = Internal.createIndex("FKJ7YBAXR6IN6VUA1KLM2I10YEK_INDEX_F", R_URIAGE_KEIJO_TEISEI.r_uriage_keijo_teisei, new OrderField[] { R_URIAGE_KEIJO_TEISEI.r_uriage_keijo_teisei.URIAGE_RIREKI_ID }, false);
        public static Index PRIMARY_KEY_F = Internal.createIndex("PRIMARY_KEY_F", R_URIAGE_KEIJO_TEISEI.r_uriage_keijo_teisei, new OrderField[] { R_URIAGE_KEIJO_TEISEI.r_uriage_keijo_teisei.URIAGE_KEIJO_ID }, true);
        public static Index UK_5FQTU9VF1ITABDUFF88E46ALY_INDEX_F = Internal.createIndex("UK_5FQTU9VF1ITABDUFF88E46ALY_INDEX_F", R_URIAGE_KEIJO_TEISEI.r_uriage_keijo_teisei, new OrderField[] { R_URIAGE_KEIJO_TEISEI.r_uriage_keijo_teisei.RECORD_ID }, true);
        public static Index FKBIIJRTBUHPP07F2W0VMMA3P8F_INDEX_5 = Internal.createIndex("FKBIIJRTBUHPP07F2W0VMMA3P8F_INDEX_5", R_URIAGE_MEISAI.r_uriage_meisai, new OrderField[] { R_URIAGE_MEISAI.r_uriage_meisai.URIAGE_ID }, false);
        public static Index FKMGXRSBS5HMJ32AOXEMX6UROQR_INDEX_5 = Internal.createIndex("FKMGXRSBS5HMJ32AOXEMX6UROQR_INDEX_5", R_URIAGE_MEISAI.r_uriage_meisai, new OrderField[] { R_URIAGE_MEISAI.r_uriage_meisai.SHOHIN_ID }, false);
        public static Index PRIMARY_KEY_5 = Internal.createIndex("PRIMARY_KEY_5", R_URIAGE_MEISAI.r_uriage_meisai, new OrderField[] { R_URIAGE_MEISAI.r_uriage_meisai.MEISAI_NUMBER, R_URIAGE_MEISAI.r_uriage_meisai.URIAGE_ID }, true);
        public static Index UK_DHV8J2AK6U0FRWNAWO7MHUDEP_INDEX_5 = Internal.createIndex("UK_DHV8J2AK6U0FRWNAWO7MHUDEP_INDEX_5", R_URIAGE_MEISAI.r_uriage_meisai, new OrderField[] { R_URIAGE_MEISAI.r_uriage_meisai.RECORD_ID }, true);
        public static Index PRIMARY_KEY_4 = Internal.createIndex("PRIMARY_KEY_4", T_SEIKYU.t_seikyu, new OrderField[] { T_SEIKYU.t_seikyu.KOKYAKU_ID, T_SEIKYU.t_seikyu.SEIKYU_DATE }, true);
        public static Index UK_4SCPAF4QMJRAM1VXMKUYJ269J_INDEX_4 = Internal.createIndex("UK_4SCPAF4QMJRAM1VXMKUYJ269J_INDEX_4", T_SEIKYU.t_seikyu, new OrderField[] { T_SEIKYU.t_seikyu.RECORD_ID }, true);
        public static Index FK9AXJO7K5P73YYEWXSIC6HT04W_INDEX_E = Internal.createIndex("FK9AXJO7K5P73YYEWXSIC6HT04W_INDEX_E", T_SEIKYU_MEISAI.t_seikyu_meisai, new OrderField[] { T_SEIKYU_MEISAI.t_seikyu_meisai.URIKAKE_ID }, false);
        public static Index PRIMARY_KEY_ED = Internal.createIndex("PRIMARY_KEY_ED", T_SEIKYU_MEISAI.t_seikyu_meisai, new OrderField[] { T_SEIKYU_MEISAI.t_seikyu_meisai.SEIKYU_ID, T_SEIKYU_MEISAI.t_seikyu_meisai.URIKAKE_ID }, true);
        public static Index UK_R4P4GM00P10V2HXDQSBCNVPP0_INDEX_E = Internal.createIndex("UK_R4P4GM00P10V2HXDQSBCNVPP0_INDEX_E", T_SEIKYU_MEISAI.t_seikyu_meisai, new OrderField[] { T_SEIKYU_MEISAI.t_seikyu_meisai.RECORD_ID }, true);
        public static Index FKK5EMLYFTBF5PBADDC4QFNMGLH_INDEX_1 = Internal.createIndex("FKK5EMLYFTBF5PBADDC4QFNMGLH_INDEX_1", T_SHOHIN_IDO.t_shohin_ido, new OrderField[] { T_SHOHIN_IDO.t_shohin_ido.BUSHO_ID }, false);
        public static Index PRIMARY_KEY_1 = Internal.createIndex("PRIMARY_KEY_1", T_SHOHIN_IDO.t_shohin_ido, new OrderField[] { T_SHOHIN_IDO.t_shohin_ido.ID }, true);
        public static Index UK_9HUM7RYAHI4JAIGE2V6TD9M13_INDEX_1 = Internal.createIndex("UK_9HUM7RYAHI4JAIGE2V6TD9M13_INDEX_1", T_SHOHIN_IDO.t_shohin_ido, new OrderField[] { T_SHOHIN_IDO.t_shohin_ido.RECORD_ID }, true);
        public static Index FKNYESTD5U0GAEDM2AUNTF943D1_INDEX_2 = Internal.createIndex("FKNYESTD5U0GAEDM2AUNTF943D1_INDEX_2", T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai, new OrderField[] { T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai.SHOHIN_ID }, false);
        public static Index FKP0LRETW08695Q1FOIS2EK6Q6B_INDEX_2 = Internal.createIndex("FKP0LRETW08695Q1FOIS2EK6Q6B_INDEX_2", T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai, new OrderField[] { T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai.SHOHIN_IDO_ID }, false);
        public static Index PRIMARY_KEY_25 = Internal.createIndex("PRIMARY_KEY_25", T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai, new OrderField[] { T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai.MEISAI_NUMBER, T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai.SHOHIN_IDO_ID }, true);
        public static Index UK_QMKOJB8N58COAAOK60IQVBJ5B_INDEX_2 = Internal.createIndex("UK_QMKOJB8N58COAAOK60IQVBJ5B_INDEX_2", T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai, new OrderField[] { T_SHOHIN_IDO_MEISAI.t_shohin_ido_meisai.RECORD_ID }, true);
        public static Index FKTKU4TUGX74WKRAT36T4WWNWEB_INDEX_7 = Internal.createIndex("FKTKU4TUGX74WKRAT36T4WWNWEB_INDEX_7", T_SHOHIN_ZAIKO.t_shohin_zaiko, new OrderField[] { T_SHOHIN_ZAIKO.t_shohin_zaiko.SHOHIN_ID }, false);
        public static Index PRIMARY_KEY_78 = Internal.createIndex("PRIMARY_KEY_78", T_SHOHIN_ZAIKO.t_shohin_zaiko, new OrderField[] { T_SHOHIN_ZAIKO.t_shohin_zaiko.BUSHO_ID, T_SHOHIN_ZAIKO.t_shohin_zaiko.EIGYO_DATE, T_SHOHIN_ZAIKO.t_shohin_zaiko.SHOHIN_ID }, true);
        public static Index UK_97I9YEJ178G3KQCKRFJ4GJH0N_INDEX_7 = Internal.createIndex("UK_97I9YEJ178G3KQCKRFJ4GJH0N_INDEX_7", T_SHOHIN_ZAIKO.t_shohin_zaiko, new OrderField[] { T_SHOHIN_ZAIKO.t_shohin_zaiko.RECORD_ID }, true);
        public static Index FK12V4JQQCK1CDREC88PF37AU7D_INDEX_4 = Internal.createIndex("FK12V4JQQCK1CDREC88PF37AU7D_INDEX_4", T_URIAGE.t_uriage, new OrderField[] { T_URIAGE.t_uriage.KOKYAKU_ID }, false);
        public static Index PRIMARY_KEY_4D = Internal.createIndex("PRIMARY_KEY_4D", T_URIAGE.t_uriage, new OrderField[] { T_URIAGE.t_uriage.DENPYO_NUMBER, T_URIAGE.t_uriage.KOKYAKU_ID }, true);
        public static Index UK_9F7L0VVJA040FK4UIR58IBQUN_INDEX_4 = Internal.createIndex("UK_9F7L0VVJA040FK4UIR58IBQUN_INDEX_4", T_URIAGE.t_uriage, new OrderField[] { T_URIAGE.t_uriage.RECORD_ID }, true);
        public static Index FKEFJRU4SE98KKBOBDKHDJC8IIM_INDEX_B = Internal.createIndex("FKEFJRU4SE98KKBOBDKHDJC8IIM_INDEX_B", T_URIAGE_MEISAI.t_uriage_meisai, new OrderField[] { T_URIAGE_MEISAI.t_uriage_meisai.URIAGE_ID }, false);
        public static Index FKR12W6PQB2GNMH56OFRHY0LOSC_INDEX_B = Internal.createIndex("FKR12W6PQB2GNMH56OFRHY0LOSC_INDEX_B", T_URIAGE_MEISAI.t_uriage_meisai, new OrderField[] { T_URIAGE_MEISAI.t_uriage_meisai.SHOHIN_ID }, false);
        public static Index PRIMARY_KEY_B = Internal.createIndex("PRIMARY_KEY_B", T_URIAGE_MEISAI.t_uriage_meisai, new OrderField[] { T_URIAGE_MEISAI.t_uriage_meisai.MEISAI_NUMBER, T_URIAGE_MEISAI.t_uriage_meisai.URIAGE_ID }, true);
        public static Index UK_QRBW4ETEB9S2JK3SPMEXAULIG_INDEX_B = Internal.createIndex("UK_QRBW4ETEB9S2JK3SPMEXAULIG_INDEX_B", T_URIAGE_MEISAI.t_uriage_meisai, new OrderField[] { T_URIAGE_MEISAI.t_uriage_meisai.RECORD_ID }, true);
        public static Index PRIMARY_KEY_53 = Internal.createIndex("PRIMARY_KEY_53", T_URIKAKE.t_urikake, new OrderField[] { T_URIKAKE.t_urikake.URIAGE_ID }, true);
        public static Index UK_3RCPSMXXRKKRO42FWTDIPIAXB_INDEX_5 = Internal.createIndex("UK_3RCPSMXXRKKRO42FWTDIPIAXB_INDEX_5", T_URIKAKE.t_urikake, new OrderField[] { T_URIKAKE.t_urikake.RECORD_ID }, true);
    }
}