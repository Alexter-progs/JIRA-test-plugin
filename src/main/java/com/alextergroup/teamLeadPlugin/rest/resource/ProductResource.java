package com.alextergroup.teamLeadPlugin.rest.resource;

import com.alextergroup.teamLeadPlugin.DAO.DAOFactory;
import com.alextergroup.teamLeadPlugin.entity.ProductEntity;
import com.alextergroup.teamLeadPlugin.pojo.Product;
import com.alextergroup.teamLeadPlugin.pojo.impl.ProductImpl;
import com.alextergroup.teamLeadPlugin.rest.model.ProductModel;
import com.alextergroup.teamLeadPlugin.rest.model.ProductsModel;
import com.alextergroup.teamLeadPlugin.rest.model.Mapper;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.rest.v1.util.CacheControl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/products")
@AnonymousAllowed
public class ProductResource {

    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final SimpleDateFormat dateFmt = new SimpleDateFormat("dd.mm.yyyy");

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProducts(){
        List<ProductModel> products = Lists.newArrayList();
        try {
            for (ProductEntity product : DAOFactory.getInstance().getProductDAO().getProducts()) {
                products.add(Mapper.toProductModel(product));
            }
        } catch (Exception e){
            log.error("Can't get products list", e);
        }
        return Response.ok(new ProductsModel(products.size(), products)).cacheControl(CacheControl.NO_CACHE).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProductByID(@PathParam("id") String idVal) throws Exception {
        long id = Long.parseLong(idVal);
        ProductEntity entity = DAOFactory.getInstance().getProductDAO().getProductByID(id);
        return Response.ok(Mapper.toProductModel(entity)).cacheControl(CacheControl.NO_CACHE).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addProduct(String request) throws Exception {
        JSONObject json = new JSONObject(request.substring(request.indexOf("{")));

        String productName = json.getString("productName");
        String manufacturer = json.getString("manufacturer");
        String manufactureDateVal = json.getString("manufactureDate");
        String expirationDateVal = json.getString("expirationDate");
        String description = json.getString("description");

        log.error(manufactureDateVal);
        log.error(expirationDateVal);

        Date manufactureDate = parseDate(manufactureDateVal);
        Date expirationDate = parseDate(expirationDateVal);

        Product product = new ProductImpl(productName, expirationDate, manufactureDate, manufacturer, description);
        ProductEntity entity = DAOFactory.getInstance().getProductDAO().addProduct(product);


        return Response.ok(Mapper.toProductModel(entity)).cacheControl(CacheControl.NO_CACHE).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteProduct(@PathParam("id") String idVal) throws Exception {
        long id = Long.parseLong(idVal);

        ProductEntity entity = DAOFactory.getInstance().getProductDAO().deleteProduct(id);

        return Response.ok(Mapper.toProductModel(entity)).cacheControl(CacheControl.NO_CACHE).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateProduct(@PathParam("id") String idVal, String request) throws Exception {
        long id = Long.parseLong(idVal);

        JSONObject json = new JSONObject(request);

        String productName = json.getString("productName");
        String manufacturer = json.getString("manufacturer");
        String manufactureDateVal = json.getString("manufacture");
        String expirationDateVal = json.getString("expirationDate");
        String description = json.getString("description");

        Date manufactureDate = parseDate(manufactureDateVal);
        Date expirationDate = parseDate(expirationDateVal);

        Product product = new ProductImpl(productName, expirationDate, manufactureDate, manufacturer, description);
        ProductEntity entity = DAOFactory.getInstance().getProductDAO().updateProduct(id, product);

        return Response.ok(Mapper.toProductModel(entity)).cacheControl(CacheControl.NO_CACHE).build();
    }

    private static Date parseDate(String dateVal) throws ParseException {
        Date date = null;
        if (StringUtils.isNotEmpty(dateVal)) {
            try {
                date = dateFmt.parse(dateVal);
            } catch (Exception e) {
                date = null;
                log.error("Can't parse date", e);
            }
        }
        return date;
    }
}