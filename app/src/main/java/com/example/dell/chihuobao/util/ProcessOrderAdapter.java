package com.example.dell.chihuobao.util;

/**已处理订单的界面
 * Created by Zx on 2016/3/9.
 */
//public class ProcessOrderAdapter extends BaseAdapter {
//    private List<Order> mOrders=null;//ListView显示的数据
//    private int resource;//显示列表项的Layout
//    private LayoutInflater inflater;//界面生成器
//    private Context context;
//    public ProcessOrderAdapter(List<Order> orders, int resource, Context context){
//
//        this.mOrders = orders;
//        this.resource = resource;
//        this.context = context;
//        //inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//    public void setMarkerData(List<Order> orderItems)
//    {
//        mOrders = orderItems;
//    }
//
//    @Override
//    public int getCount() {
//        return mOrders.size();
//    }
//
//    @Override
//    public Order getItem(int arg0) {
//        return mOrders.get(arg0);
//    }
//    @Override
//    public long getItemId(int arg0) {
//        return arg0;
//    }
//
//    @Override
//    public View getView(int arg0, View arg1, ViewGroup arg2) {
//        ViewHolder viewHolder=null;
//        List<Item> mItems=new ArrayList<Item>();
//
//
//
//        if(arg1 == null){
//            viewHolder = new ViewHolder();
//            LayoutInflater mInflater = LayoutInflater.from(context);
//            arg1 = mInflater.inflate(resource, null);
//            viewHolder.telephone = (TextView) arg1.findViewById(R.id.order_search_result_item_telphone);
//            viewHolder.time = (TextView) arg1.findViewById(R.id.order_search_result_item_time);
//            viewHolder.address = (TextView)arg1.findViewById(R.id.order_search_result_item_address);
//            viewHolder.orderId=(TextView) arg1.findViewById(R.id.order_search_result_item_orderId);
//            viewHolder.food= (MyListView) arg1.findViewById(R.id.MyListView);//嵌套的listview
//            viewHolder.notice=(TextView) arg1.findViewById(R.id.order_notice);
//            viewHolder.item_id= (TextView) arg1.findViewById(R.id.order_search_result_item_id);
//            viewHolder.accept= (TextView) arg1.findViewById(R.id.accept_order);
//            arg1.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) arg1.getTag();
//        }
//        Order order=getItem(arg0);
//
//        if (order!=null){
//            viewHolder.telephone.setText(order.getTelephone());
//            viewHolder.time.setText(order.getOrdertime());
//            viewHolder.address.setText(order.getAddress());
//            viewHolder.orderId.setText(order.getOrderId());
//            mItems=order.getOrderdelist();
//            OrderFoodAdapter orderFoodAdapter=new OrderFoodAdapter(mItems,R.layout.item_mylistview,context);
//            viewHolder.food.setAdapter(orderFoodAdapter);
//            viewHolder.notice.setText(order.getRequest());
//            viewHolder.item_id.setText(order.getId());
//            viewHolder.accept.setOnClickListener(new lvButtonListener(arg0));
//        }
//
//
////        final Order order = mOrders.get(arg0);
////        TextView telphone= (TextView) arg1.findViewById(R.id.order_search_result_item_telphone);
////        TextView time= (TextView) arg1.findViewById(R.id.order_search_result_item_time);
////        TextView address= (TextView) arg1.findViewById(R.id.order_search_result_item_address);
////        TextView orderId= (TextView) arg1.findViewById(R.id.order_search_result_item_orderId);
////        TextView price= (TextView) arg1.findViewById(R.id.order_item_price);
////        TextView count= (TextView) arg1.findViewById(R.id.order_item_count);
////        TextView name= (TextView) arg1.findViewById(R.id.order_item_name);
////        TextView notice= (TextView) arg1.findViewById(R.id.order_notice);
////        TextView receipt= (TextView) arg1.findViewById(R.id.order_receipt);
////        TextView item_id= (TextView) arg1.findViewById(R.id.order_search_result_item_id);
////        TextView accept= (TextView) arg1.findViewById(R.id.accept_order);
//
//        return arg1;
//
//    }
//    class lvButtonListener implements View.OnClickListener {
//        private int position;
//
//        lvButtonListener(int pos) {
//            position = pos;
//        }
//
//        @Override
//        public void onClick(View v) {
//            int vid = v.getId();
//            Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context, DeliverManActivity.class);
//            context.startActivity(intent);
//        }
//    }
//
//    private  class ViewHolder
//    {
//        TextView telephone;
//        TextView time;
//        TextView address;
//        TextView orderId;
//        MyListView food;
//        TextView notice;
//        TextView item_id;
//        TextView accept;
//
//    }
//}

