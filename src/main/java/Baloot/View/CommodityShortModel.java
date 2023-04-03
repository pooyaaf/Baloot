package Baloot.View;

import Baloot.Model.CommentReportModel;
import Baloot.Model.CommodityModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommodityShortModel {
    public CommodityModel commodityModel;
    public ArrayList<CommentReportModel> commentsList;
}
