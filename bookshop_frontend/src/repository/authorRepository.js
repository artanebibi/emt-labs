import axiosInstance from "../axios/axios.js";

const productRepository = {
    findAll: async () => {
        return await axiosInstance.get("/authors");
    },
};

export default productRepository;