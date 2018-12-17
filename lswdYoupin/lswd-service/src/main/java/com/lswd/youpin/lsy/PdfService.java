package com.lswd.youpin.lsy;

import com.lswd.youpin.model.User;
import com.lswd.youpin.model.lsy.Pdf;
import com.lswd.youpin.response.LsResponse;

public interface PdfService {
    LsResponse getPdfList(User u, String keyword, Integer resTypeId, Integer pageNum, Integer pageSize);

    LsResponse getPdfById(Integer id);

    LsResponse addOrUpdatePdf(Pdf machine);

    LsResponse delPdf(Integer id);

}
