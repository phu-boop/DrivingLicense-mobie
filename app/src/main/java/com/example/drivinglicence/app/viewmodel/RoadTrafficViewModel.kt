package com.example.drivinglicence.app.viewmodel

import com.example.drivinglicence.R
import com.example.drivinglicence.app.entity.ItemRoadTraffic
import com.example.drivinglicence.component.viewmodel.BaseViewModel

class RoadTrafficViewModel : BaseViewModel() {

    fun getRoadTrafficBan(): MutableList<ItemRoadTraffic> {
        return mutableListOf(
            ItemRoadTraffic(
                "Biển số P.124a \"Cấm quay đầu xe\"",
                "Để báo cấm các loại xe quay đầu theo kiểu chữ U. Chiều mũi tên phù hợp với chiều cấm quay đầu xe. Biển số P.124a có hiệu lực cấm các laoij xe (cơ giới và thô sơ) trừ các xe dược ưu tiên theo quy định. Biển không cso giá trị cấm rẽ trái để đi sang hướng đường khác",
                R.drawable.bien_bao_p_124a
            ),
            ItemRoadTraffic(
                "Biển số P.105 \"Cấm xe ôtô và xe máy\"",
                "Để báo đường cấm các loại xe cơ giới và xe máy đi qua trừ các xe được ưu tiên theo quy định.",
                R.drawable.bien_bao_p105
            ),
            ItemRoadTraffic(
                "Biển số P.101 \"Cấm đi ngược chiều\"",
                "Để báo đường cấm các loại phương tiện đi lại cả hai hướng, trừ các xe được ưu tiên theo quy định.",
                R.drawable.bien_bao_p101
            ),
            ItemRoadTraffic(
                "Biển số P.102 \"Đường cấm\"",
                "Để báo đường cấm các loại xe (cơ giới và thô sơ) đi vào theo chiều đặt biển, trừ các xe được ưu tiên theo quy định, đặt biển số P.102 “Cấm đi ngược chiều”. Người đi bộ được phép đi trên vỉa hè hoặc lề đường.",
                R.drawable.bien_bao_p102
            ),
            ItemRoadTraffic(
                "Biển số P.103a \"Cấm xe ô tô\"",
                "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng đi qua, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định.",
                R.drawable.bien_bao_p103a
            ),
            ItemRoadTraffic(
                "Biển số P.103b \"Cấm xe ô tô rẽ phải\"",
                "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng rẽ phải, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định.",
                R.drawable.bien_bao_p103b
            ),
            ItemRoadTraffic(
                "Biển số P.103c \"Cấm xe ô tô rẽ trái\"",
                "Để báo đường cấm các loại xe cơ giới kể cả xe máy 3 bánh có thùng rẽ trái, trừ xe máy 2 bánh, xe gắn máy và các xe được ưu tiên theo quy định.",
                R.drawable.bien_bao_p103c
            ),
            ItemRoadTraffic(
                "Biển số P.104 \"Cấm xe máy\"",
                "Để báo đường cấm các loại xe máy trừ các xe được ưu tiên theo quy định, đặt biển số P.104 “Cấm xe máy”. Biển không có giá trị cấm những người dắt xe máy.",
                R.drawable.bien_bao_p104c
            ),
            ItemRoadTraffic(
                "Biển số P.106a \"Cấm xe ô tô tải\"",
                "Để báo đường cấm các loại xe ô tô tải trừ các xe được ưu tiên theo quy định. Biển có hiệu lực cấm đối với cả máy kéo và các loại xe máy chuyên dùng đi vào đoạn đường đặ biển số P.106a.",
                R.drawable.bien_bao_p106a
            ),
            ItemRoadTraffic(
                "Biển số P.106b \"Cấm xe ô tô tải\"",
                "Để báo đường cấm các loại xe ô tô tải có khối lượng chuyên chở lớn hơn một giá trị nhất định. Biển có hiệu lực cấm các xe ô tô tải có khối lượng chuyên chở lớn hơn giá trị chữ số ghi trong biển. Biển có hiệu lực cấm đối với cả máy kéo và các xe máy chuyên dùng đi vào đoạn đường đặt biển.",
                R.drawable.bien_bao_p106b
            ),
            ItemRoadTraffic(
                "Biển số P.106c \"Cấm xe chở hàng nguy hiểm\"",
                "Để báo đường cấm các loại xe hàng nguy hiểm.",
                R.drawable.bien_bao_p106c
            ),
            ItemRoadTraffic(
                "Biển số P.107 \"Cấm xe ô tô khách và xe ô tô tải\"",
                "Để báo đường cấm các xe ô tô chở khách và các loại xe ô tô tải kể cả các loại máy kéo và xe máy thi công chuyên dùng đi qua trừ các xe được ưu tiên theo quy định.",
                R.drawable.bien_bao_p107
            ),
            ItemRoadTraffic(
                "Biển số P.107a \"Cấm xe ô tô khách\"",
                "Để báo đường cấm ô tô chở khác đi qua trừ các xe ưu tiên theo quy định. Biển này không cấm xe buýt.",
                R.drawable.bien_bao_p107a
            ),
            ItemRoadTraffic(
                "Biển số P.107b \"Cấm xe ô tô taxi\"",
                "Để báo đường cấm ô tô taxi đi lại. Trường hợp cấm xe ô tô taxi theo giờ thì đặt biển phụ ghi giờ cấm.",
                R.drawable.bien_bao_p107b
            ),
            ItemRoadTraffic(
                "Biển số P.108 \"Cấm xe kéo rơ-moóc\"",
                "Để báo đường cấm các loại xe cơ giới kéo theo rơ-moóc kể cả xe máy, máy kéo, ô tô khách kéo theo rơ-moóc đi lại, trừ loại ô tô sơ-mi-rơ-moóc và các xe được ưu tiên (có kéo theo rơ-moóc) theo quy định.",
                R.drawable.bien_bao_p108
            ),
            ItemRoadTraffic(
                "Biển số P.109 \"Cấm máy kéo\"",
                "Để báo đường cấm các loại máy kéo kể cả máy kéo bánh hơi và bánh xích đi qua.",
                R.drawable.bien_bao_p109
            )
        )
    }

    fun getRoadTrafficCommand(): MutableList<ItemRoadTraffic> {
        return mutableListOf(
            ItemRoadTraffic(
                "Biển số R.122 \"Dừng lại\"",
                "Để báo các xe (cơ giới và thô sơ) dừng lại.",
                R.drawable.b_r122
            ),
            ItemRoadTraffic(
                "Biển số R.301a \"Hướng đi phải theo\"",
                "Khi đặt biển R.301a ở trước nơi đường giao nhau thì hiệu lực tác dụng của biển là ở phạm vi khu vực nơi đường giao nhau phía sau biển tức là cấm xe rẽ phải hay rẽ trái. Nếu biển này đặt ở sau nơi đường giao nhau (bắt đầu vào đoạn đường phố) thì hiệu lực tác dụng của biển là từ vị trí đặt biển đến nơi đường giao nhau. Trong trường hợp này cấm rẽ trái và quay đầu trong vùng tác dụng của biển, chỉ cho phép rẽ phải vào cổng nhà hoặc ngõ phố có trên đoạn đường từ nơi giao nhau đặt biển đến nơi đường giao nhau tiếp theo.",
                R.drawable.b_r301a
            ),
            ItemRoadTraffic(
                "Biển số R.301b \"Hướng đi phải theo\"",
                "Nhằm chỉ hướng cho phép xe đi ngang qua nơi đường giao nhau và ngăn chặn hướng đi ngược chiều trên đường phố với đường một chiều. Biển bắt buộc người tham gia giao thông chỉ được phép rẽ phải hoặc rẽ trái ở phạm vị nơi đường giao nhau trước mặt biển.",
                R.drawable.b_r301b
            ),
            ItemRoadTraffic(
                "Biển số R.301c \"Hướng đi phải theo\"",
                "Nhằm chỉ hướng cho phép xe đi ngang qua nơi đường giao nhau và ngăn chặn hướng đi ngược chiều trên đường phố với đường một chiều. Biển bắt buộc người tham gia giao thông chỉ được phép rẽ phải hoặc rẽ trái ở phạm vị nơi đường giao nhau trước mặt biển.",
                R.drawable.b_r301c
            ),
            ItemRoadTraffic(
                "Biển số R.301d \"Hướng đi phải theo\"",
                "Biển bắt buộc người tham gia giao thông chỉ được phép rẽ phải hoặc rẽ trái ở phạm vi nơi đường giao nhau đằng sau mặt biển.",
                R.drawable.b_r301d
            ),
            ItemRoadTraffic(
                "Biển số R.301e \"Hướng đi phải theo\"",
                "Biển bắt buộc người tham gia giao thông chỉ được phép rẽ phải hoặc rẽ trái ở phạm vi nơi đường giao nhau đằng sau mặt biển.",
                R.drawable.b_r301e
            ),
            ItemRoadTraffic(
                "Biển số R.301h \"Hướng đi phải theo\"",
                "Người tham gia giao thông chỉ được phép đi thẳng hay rẽ trái và được phép quay đầu xe để đi theo hướng ngược lại.",
                R.drawable.b_r301h
            ),
            ItemRoadTraffic(
                "Biển số R.301f \"Hướng đi phải theo\"",
                "Người tham gia giao thông chỉ được phép đi thẳng hay rẽ trái và được phép quay đầu xe để đi theo hướng ngược lại.",
                R.drawable.b_r301f
            ),
            ItemRoadTraffic(
                "Biển số R.303 \"Nơi giao nhau chạy theo vòng xuyến\"",
                "Để báo cho các loại xe ( thô sơ và cơ giới) phải chạy vòng theo đảo an toàn ở nơi đường giao nhau. Biển có hiệu lực bắt buộc các loại xe muốn chuyển hướng phải chạy vòng theo đảm an toàn theo hướng mũi tên.",
                R.drawable.b_r303
            ),
            ItemRoadTraffic(
                "Biển số R.305 \"Đường dành cho người đi bộ\"",
                "Để báo đường dành cho người đi bộ. Biển có hiệu lực bắt buộc người đi bộ phải dùng đường dành riêng này để đi. Các loại xe cơ giới và thô sơ (trừ xe đạp và xe lăn dành cho người tàn tật), kể cả các xe được ưu tiên theo quy định không được phép đi vào trên đường đã đặt biển này, trừ trường hợp đi cắt ngang qua nhưng phải đảm bảo tuyệt đối an toàn cho người đi bộ.",
                R.drawable.b_r305
            ),
            ItemRoadTraffic(
                "Biển số R.306 \"Tốc độ tối thiểu cho phép\"",
                "Để báo tốc độ tối thiểu cho phép các xe cơ giới chạy. Biển có hiệu lực bắt buộ các loại xe cơ giới vận hành với tốc độ không nhỏ hơn trị số ghi trên biển trong điều kiện giao thông thuận lợi và an toàn. Các loại xe có tốc độ tối đa theo quy định của nhà sản xuất không đạt tốc độ tối thiểu đã ghi trên biển thì không được phép đi vào đường này. Trị số ghi trên biển được tính bằng km/h và được quy định tùy theo tiêu chuẩn kỹ thuật của cầu đường và tổ chức giao thông, không được quy định trị số lớn hơn trị số tốc độ an toàn.",
                R.drawable.b_r306
            ),
            ItemRoadTraffic(
                "Biển số R.307 \"Hết hạn chế tốc độ tối thiểu\"",
                "Biển có giá trị báo cho người tham gia giao thông biết hiệu lực của biển số R.306 hết tác dụng, kể từ biển này các xe được phép chạy chậm hơn trị số ghi trên biển nhưng không được gây cản trở các xe khác.",
                R.drawable.b_r307
            ),
        )
    }

    fun getRoadTrafficInstruction(): MutableList<ItemRoadTraffic> {
        return mutableListOf(
            ItemRoadTraffic(
                "Biển số 401 \"Bắt đầu đường ưu tiên\"",
                "Báo hiệu hết đoạn đường được ưu tiên",
                R.drawable.bien402
            ),
            ItemRoadTraffic(
                "Biển số 402 \"Hết đoạn đường ưu tiên\" ",
                "Khi đặt biển R.301a ở trước nơi đường giao nhau thì hiệu lực tác dụng của biển là ở phạm vi khu vực nơi đường giao nhau phía sau biển tức là cấm xe rẽ phải hay rẽ trái. Nếu biển này đặt ở sau nơi đường giao nhau (bắt đầu vào đoạn đường phố) thì hiệu lực tác dụng của biển là từ vị trí đặt biển đến nơi đường giao nhau. Trong trường hợp này cấm rẽ trái và quay đầu trong vùng tác dụng của biển, chỉ cho phép rẽ phải vào cổng nhà hoặc ngõ phố có trên đoạn đường từ nơi giao nhau đặt biển đến nơi đường giao nhau tiếp theo.",
                R.drawable.b_r301a
            ),
            ItemRoadTraffic(
                "Biển số 403a \"Đường dành cho ôtô”",
                "Để chỉ dẫn bắt đầu đường dành cho các loại ôtô đi lại, các loại phương tiện giao thông khác không được phép đi vào đoạn đường có dặt biển này\n",
                R.drawable.bien403a
            ),
            ItemRoadTraffic(
                "Biển số 403b “Đường dành cho ô tô, xe máy\"",
                "Để chỉ dẫn bắt đầu đường dành cho các loại ôtô, xe máy (kể cả xe gắn máy) đi lại, các loại phương tiện giao thông khác không được phép đi vào đoạn đường có đặt biển này",
                R.drawable.bien403b
            ),
            ItemRoadTraffic(
                "Biển số 404a \"Hết đường dành cho ô tô”",
                "Để chỉ dẫn hết đoạn đường dành cho ôtô đi lại",
                R.drawable.bien404a
            ),
            ItemRoadTraffic(
                "Biển số 404b “Hết đường dành cho ô tô, xe máy\"",
                "Để chỉ dẫn hết đoạn đường dành cho ôtô, xe máy đi lại\n",
                R.drawable.bien404b
            ),
            ItemRoadTraffic(
                "Biển số 405a \"Đường cụt\"",
                "Để chỉ dẫn đường cụt, lối rẽ vào đường cụt phía bên phải",
                R.drawable.bien405a
            ),
            ItemRoadTraffic(
                "Biển số 405b \"Đường cụt\"",
                "Để chỉ dẫn đường cụt, lối rẽ vào đường cụt phía bên trái",
                R.drawable.bien405b
            ),
            ItemRoadTraffic(
                "Biển số 405c \"Đường cụt\"",
                "Để chỉ dẫn phía trước là đường cụt, đặt trước đường cụt 300m đến 500m và cứ 100m phải đặt thêm một biển",
                R.drawable.bien405c
            ),
            ItemRoadTraffic(
                "Biển số 406 \"Được ưu tiên qua đường hẹp\" ",
                "Để chỉ dẫn cho người lái xe cơ giới biết mình được quyền ưu tiên đi trước trên đoạn đường hẹp. Nếu trên hướng đi ngược chiều có xe (cơ giới hoặc thô sơ) đã đi vào phạm vi đường hẹp thì xe đi theo chiều ưu tiên cũng phải nhường đường.\n",
                R.drawable.bien406
            ),
            ItemRoadTraffic(
                "Biển số 409 \"Chỗ quay xe\" ",
                "Để chỉ dẫn vị trí được phép quay đầu xe. Biển không cho phép rẽ trái (trừ các xe được quyền ưu tiên theo quy định).",
                R.drawable.bien409
            ),
            ItemRoadTraffic(
                "Biển số 411 \"Hướng đi trên mỗi làn đường theo vạch kẻ đường\" ",
                "Để chỉ dẫn cho người lái xe biết số lượng làn đường trên mặt đường và hướng đi trên mỗi làn đường theo vạch kẻ đường.\n" +
                        "\n" +
                        "Biển sử dụng phối hợp với vạch kẻ đường (loại vạch 1.18 hình mũi tên màu trắng trên mặt đường). Tùy theo tình hình thực tế về số lượng làn đường và hướng đi trên mỗi làn đường mà có ký hiệu chỉ dẫn phù hợp. Biển có tác dụng bắt buộc người lái xe phải đi đúng làn đường đã được chỉ dẫn hướng phù hợp với hành trình cuả xe.",
                R.drawable.bien411
            ),
            ItemRoadTraffic(
                "Biển số 415 \"Mũi tên chỉ hướng đi\" ",
                "Được đặt trong khu đông dân cư, ở các đường giao nhau để chỉ dẫn hướng đi đến một địa danh lân cận tiếp theo và khoảng cách (km) đến nơi đó. Nếu biển này đặt trên đường cao tốc thì phía bên trái biển có thêm hình vẽ đường cao tốc.",
                R.drawable.bien415
            )
        )
    }

    fun getRoadTrafficWarning(): MutableList<ItemRoadTraffic> {
        return mutableListOf(
            ItemRoadTraffic(
                "Biển số W.201 (a,b) \"Chỗ ngoặt nguy hiểm\"",
                "Để báo trước sắp đến một chỗ ngoặt nguy hiểm\n" +
                        "- Biển số W.201a chỗ ngoặt nguy hiểm vòng bên trái;\n" +
                        "- Biển số W.201b chỗ ngoặt nguy hiểm vòng bên phải.\n" + "Biển này thường đặt ở vị trí đường cong; ở những vùng mà việc quan sát của người tham gia giao thông gặp khó khăn như vùng cây rậm rạp, vùng thường có sương mù hoặc sau đoạn thẳng dài từ 1 km trở lên có đường cong đầu tiên.",
                R.drawable.biencc201ab
            ),
            ItemRoadTraffic(
                "Biển số W.201 (c,d) \"Chỗ ngoặt nguy hiểm có nguy cơ lật xe\"",
                "Để báo trước sắp đến chỗ ngoặt nguy hiểm có khả năng gây lật các xe có trọng tâm cao và tải trọng lớn như xe tải, xe buýt giường nằm, xe chở chất lỏng, v.v...\n" +
                        "- Biển W.201c chỗ ngoặt nguy hiểm có nguy cơ lật xe bên phải khi đường cong vòng sang trái ;\n" +
                        "- Biển W.201d chỗ ngoặt nguy hiểm có nguy cơ lật xe bên trái khi đường cong vòng bên phải.",
                R.drawable.biencc201cd
            ),
            ItemRoadTraffic(
                "Biển số W.202 (a,b) \"Nhiều chỗ ngoặt nguy hiểm liên tiếp\"",
                "Để báo trước sắp đến hai chỗ ngoặt ngược chiều nhau liên tiếp\n" +
                        "- Biển W.202a đặt trong trường hợp có từ 2 chỗ ngoặt, ở gần nhau trong đó có ít nhất một chỗ ngoặt nguy hiểm mà chỗ ngoặt đầu tiên hướng vòng bên trái;\n" +
                        "- Biển W.202b đặt trong trường hợp như biển số 202a nhưng hướng vòng bên phải.",
                R.drawable.biencc202ab
            ),
            ItemRoadTraffic(
                "Biển số W.203 (a,b,c) \"Đường bị thu hẹp\"",
                "Để báo trước sắp đến một đoạn đường bị thu hẹp đột ngột\n" +
                        "- Biển số W.203a đặt trong trường hợp đường bị thu hẹp cả hai bên;\n" +
                        "- Biển số W.203b hoặc biển số W.203c đặt trong trường hợp đường bị thu hẹp về phía trái hoặc phía phải.\n" +
                        "Nếu đường bị thu hẹp đến mức không có khả năng thông qua cho hai xe đi ngược chiều thì phải đặt trước vị trí thu hẹp các biển xác định quyền ưu tiên của chiều đi (biển số P.132 và biển số I.406). Xe đi ở chiều đường bị thu hẹp phải nhường đường cho xe đi ngược chiều. Nếu trước vị trí bị thu hẹp có đặt biển số P.132 thì phải nhường cho xe chạy ngược chiều; nếu đặt biển số I.406, thì xe được ưu tiên qua đường hẹp trước và xe ngược chiều có trách nhiệm chờ đợi.",
                R.drawable.biencc203
            ),
            ItemRoadTraffic(
                "Biển số W.205 (a,b,c,d,e) \"Đường giao nhau\"",
                "Biển này dùng để báo trước sắp đến nơi giao nhau cùng mức của các tuyến đường cùng cấp (không có đường nào ưu tiên) trên cùng một mặt bằng. Biển được đặt trước nơi đường giao nhau ở ngoài phạm vi nội thành, nội thị là chủ yếu.",
                R.drawable.biencc205
            ),
            ItemRoadTraffic(
                "Biển số W.206 \"Giao nhau chạy theo vòng xuyến\"",
                "Dùng để báo trước nơi giao nhau có bố trí đảo an toàn ở giữa nút giao, các loại xe qua nút giao phải đi vòng xuyến quanh đảo an toàn theo chiều mũi tên.",
                R.drawable.biencc206
            ),
            ItemRoadTraffic(
                "Biển số W.209 \"Giao nhau có tín hiệu đèn\"",
                "Để báo trước nơi giao nhau có điều khiển giao thông bằng tín hiệu đèn trong trường hợp người tham gia giao thông khó quan sát thấy đèn để kịp thời xử lý. Đây là biển báo hiệu sắp đến chỗ giao nhau nguy hiểm.",
                R.drawable.biencc209
            ),
            ItemRoadTraffic(
                "Biển số W.210 \" Giao nhau với đường sắt có rào chắn\"",
                "",
                R.drawable.biencc210
            ),
            ItemRoadTraffic(
                "Biển số W.212 \"Cầu hẹp\"",
                "Để báo trước sắp đến cầu hẹp là loại cầu có chiều rộng phần xe chạy nhỏ hơn hoặc bằng 4,50 m phải đặt biển số W.212 \"Cầu hẹp\". Khi qua các cầu này lái xe phải đi chậm, quan sát, nhường nhau và dừng lại chờ ở hai đầu cầu.",
                R.drawable.biencc212
            ),
            ItemRoadTraffic(
                "Biển số W.226 \"Đường người đi xe đạp cắt ngang\"",
                "Để báo trước là gần tới vị trí thường có người đi xe đạp từ những đường nhỏ cắt ngang qua hoặc từ đường dành cho xe đạp đi nhập vào đường ôtô, phải đặt biển số W.226 \"Đường người đi xe đạp cắt ngang\".",
                R.drawable.biencc216
            ),
            ItemRoadTraffic(
                " Biển số W.217 \"Bến phà\"",
                "Để báo trước sắp đến bến phà, phải đặt biển số W.217 \"Bến phà\". Người tham gia giao thông phải tuân theo nội quy bến phà.",
                R.drawable.biencc217
            ),
            ItemRoadTraffic(
                "Biển số W.225 \"Trẻ em\"",
                "a) Để báo trước là gần đến đoạn đường thường có trẻ em đi ngang qua hoặc tụ tập trên đường như ở vườn trẻ, trường học, câu lạc bộ, phải đặt biển số W.225 \"Trẻ em\".\n" +
                        "b) Chiều dài của đoạn đường này được chỉ dẫn bằng biển số S.501 \"Phạm vi tác dụng của biển\" đặt bên dưới biển chính.\n" +
                        "c) Gặp biển này, người tham gia giao thông phải đi chậm và thận trọng đề phòng khả năng xuất hiện và di chuyển bất ngờ của trẻ em trên mặt đường.",
                R.drawable.biencc225
            )
        )
    }

    fun getRoadTrafficSub(): MutableList<ItemRoadTraffic> {
        return mutableListOf(
            ItemRoadTraffic(
                "Biển số 501. \"Phạm vi tác dụng của biển\"",
                "Để thông báo chiều dài đoạn đường nguy hiểm hoặc cấm hoặc hạn chế bên dưới một số biển báo nguy hiểm, biển báo cấm hoặc hạn chế, chẳng hạn như: Nhiều chỗ ngoặt nguy hiểm liên tiếp; Dốc xuống nguy hiểm...",
                R.drawable.bienphu501
            ),
    ItemRoadTraffic(
                "Biển số 502 \"Khoảng cách đến đối tượng báo hiệu\"",
                "Bên dưới các loại biển báo nguy hiểm, biển báo cấm, biển hiệu lệnh và chỉ dẫn, thông báo khoảng cách thực tế từ vị trí đặt biển đến đối tượng báo hiệu ở phía trước.",
                R.drawable.bienphu502
            ),
    ItemRoadTraffic(
                "Biển số 504 \"Làn đường\"",
                "Biển số 504 được đặt bên trên làn đường và dưới các biển báo cấm và biển hiệu lệnh hay bên dưới đèn tín hiệu để chỉ làn đường chịu hiệu lực của biển báo hay đèn tín hiệu.",
                R.drawable.bienphu504
            ),
    ItemRoadTraffic(
                "Biển số 505a \"Loại xe\"",
                "Được đặt bên dưới các biển báo cấm và biển hiệu lệnh hay biển chỉ dẫn để chỉ loại xe chịu hiệu lực của biển báo cấm, biển hiệu lệnh hay biển chỉ dẫn. Tùy theo loại xe chịu hiệu lực mà bố trí hình vẽ cho phù hợp.",
                R.drawable.bienphu505
            ),
    ItemRoadTraffic(
                "Biển số 505b \"Loại xe hạn chế qua cầu\"",
                "Được đặt bên dưới biển báo số 106a “Cấm ôtô tải” để chỉ các loại xe tải chịu hiệu lực của biển báo và trọng lượng lớn nhất cho phép (bao gồm trọng lượng xe và hàng) tương ứng với mỗi loại xe không phụ thuộc vào số lượng trục.",
                R.drawable.bienphu505b
            ),
   ItemRoadTraffic(
                "Biển số 505(c) \"Tải trọng trục hạn chế qua cầu\"",
                "Được đặt bên dưới biển báo số 106a “Cấm ôtô tải” để chỉ các loại xe tải có tải  trọng  trục  lớn nhất cho phép tương ứng với mỗi loại  trục (trục đơn, trục kép, trục ba).",
                R.drawable.bienphu505c
            ),
   ItemRoadTraffic(
                "Biển số 506a \"Hướng đường ưu tiên\"",
                "Biển số 506a  được đặt bên dưới biển chỉ dẫn số 401 trên đường ưu tiên để chỉ dẫn cho người lái xe trên đường này biết hướng đường ưu tiên ở ngã tư.",
                R.drawable.bienphu506a
            ),
   ItemRoadTraffic(
                "Biển số 507 \"Hướng rẽ\"",
                "Được sử dụng độc lập để báo trước cho người tham gia giao thông biết chuẩn bị đến chỗ rẽ nguy hiểm và để chỉ hướng rẽ.\n",
                R.drawable.bien507
            ),
   ItemRoadTraffic(
                "Biển số 509 \"Thuyết minh biển chính\"",
                "Để bổ sung cho biển số 130 \"Cấm dừng, đỗ xe\", biển số 131 (a,b,c) \"Cấm đỗ xe\", phải đặt thêm biển số 509b \"Cấm đỗ xe\" bên dưới biển số 130, 131 (a,b,c)",
                R.drawable.bien509b
            )
            )
    }
}