// import {defineStore} from 'pinia'
// import {ref , computed} from 'vue'
// import router from '@/router'
// import { useRoute } from 'vue-router'
// import axios from 'axios'

// const REST_ARTICLE_API = `http://localhost:8080/articleapi`

// const categoryMap = {
//   '다이어트': 'diet',
//   '운동': 'exercise',
//   '전문가조언': 'advice'
// }

// export const useArticleStore = defineStore('article', ()=>{
//   const articleList = ref([])
//   const accessToken = ref('')
//   const route = useRoute();

//   const itemsPerPage = 5;
//   const currentPage = ref(1);
//   const totalItemCount = ref(0);

//   const pagination = ref([])

//   const setCurrentPage = function(pageNum){
//     currentPage.value = pageNum;
//   }

//   // const getArticleList = function () {
//   //   const storeObj = JSON.parse(sessionStorage.getItem('user'));
//   //   accessToken.value = storeObj.accessToken;

//   //   axios.get(`${REST_ARTICLE_API}/article`,
//   //   {
//   //     headers: {
//   //         "access-token": accessToken.value
//   //     }
//   //  })
//   //   .then((response) => {
//   //     articleList.value = response.data
//   //     })
//   //     .catch((e)=>{
//   //       console.log('article list 가져오기 실패')
//   //     })
//   // }

//   // 카테고리 별 리스트 불러오기(페이지네이션이 안된 버젼)
//   /*
//   const getArticleListByCategory = function (category) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.get(`${REST_ARTICLE_API}/article?category=${category}`,
//     {
//       headers: {
//           "access-token": accessToken.value
//       }
//    })
//     .then((response) => {
//       articleList.value = response.data
//       })
//       .catch((e)=>{
//         console.log('article list 가져오기 실패')
//       })
//   }*/

//   // 페이지네이션을 고려해서 가져오기
//   const getArticleListByCategory = function (category) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.get(`${REST_ARTICLE_API}/article?category=${category}&currentPage=${currentPage.value}`,
//     {
//       headers: {
//           "access-token": accessToken.value
//       }
//    })
//     .then((response) => {
//       articleList.value = response.data
//       })
//       .catch((e)=>{
//         console.log('article list 가져오기 실패')
//       })
//   }

//   // 페이지네이션을 가져오기
//   const getPagination = function (category) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.get(`${REST_ARTICLE_API}/pagination?category=${category}&currentPage=${currentPage.value}`,
//     {
//       headers: {
//           "access-token": accessToken.value
//       }
//    })
//     .then((response) => {
//       pagination.value = response.data
//       })
//       .catch((e)=>{
//         console.log('article list 가져오기 실패')
//       })
//   }

//   // // 카테고리 별 전체 글 개수 가져오기(테스트)
//   // const getTotalArticleCountByCategory = function (category) {
//   //   const storeObj = JSON.parse(sessionStorage.getItem('user'));
//   //   accessToken.value = storeObj.accessToken;

//   //   axios.get(`${REST_ARTICLE_API}/total-article-count?category=${category}`,
//   //   {
//   //     headers: {
//   //         "access-token": accessToken.value
//   //     }
//   //  })
//   //   .then((response) => {
//   //     totalItemCount.value = response.data
//   //     console.log("전체 글 개수:")
//   //     console.log(totalArticleCount)
//   //     })
//   //     .catch((e)=>{
//   //       console.log('article list 가져오기 실패')
//   //     })
//   // }

//   //기사 한개 불러오기
//   const article = ref([])
//   const getArticle = function (articleId) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.get(`${REST_ARTICLE_API}/article/${articleId}`,
//     {
//       headers: {
//           "access-token": accessToken.value
//       }
//     })
//       .then((response) => {
//       article.value = response.data
//     })
//   }

//   //기사 등록
//   const createArticle = function (article) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios({
//       url: `${REST_ARTICLE_API}/article`,
//       method: 'POST',
//       headers: {
//         "Content-Type": "application/json",
//         "access-token": accessToken.value
//       },
//       data: article
//     })
//       .then(() => {
//         const currentCategory = categoryMap[category.value];
//         router.push({ name: 'category', params: { category: currentCategory }})
//       })
//       .catch((err) => {
//       console.log(err)
//     })
//   }

//   //기사 수정
//   const updateArticle = function ( articleId ) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.put(`${REST_ARTICLE_API}/article/${articleId}`, article.value,
//     {
//       headers: {
//           "access-token": accessToken.value
//       }
//     })
//       .then(() => {
//         router.push({ name: 'articleDetail', params: { id: articleId }});
//     })
//     .catch((err) => {
//       console.log(err)
//     })
//   }

//   //기사 검색
//   const searchArticleList = function (searchCondition) {
//     console.log('aaae')
//     console.log(searchCondition);
//     console.log(route.params.category);

//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.get(`${REST_ARTICLE_API}/search`, {
//       params: {
//         category: route.params.category,
//         key: searchCondition.key,
//         word: searchCondition.word,
//         orderBy: searchCondition.orderBy,
//         orderByDir: searchCondition.orderByDir

//       },
//       headers: {
//         "access-token": accessToken.value
//       }
//     })
//       .then((res) => {
//         console.log(res.data);
//         articleList.value = res.data
//     })
//     .catch((err) => {
//       console.log(err)
//     })
//   }

//   //기사 삭제
//   const deleteArticle = function (articleId) {
//     const storeObj = JSON.parse(sessionStorage.getItem('user'));
//     accessToken.value = storeObj.accessToken;

//     axios.get(`${REST_ARTICLE_API}/article/${articleId}`, {
//       headers: {
//         "access-token": accessToken.value
//       }
//     })
//     .then((response) => {
//       const article = response.data;

//       axios.delete(`${REST_ARTICLE_API}/article/${articleId}`, {
//         headers: {
//           "access-token": accessToken.value
//         }
//       })
//       .then(() => {
//         const category = categoryMap[article.category];
//         router.push({ name: 'category', params: { category: category } });
//       })
//       .catch((err) => {
//         console.log(err);
//       });
//     })
//     .catch((err) => {
//       console.log(err);
//     });
//   };

//   return { articleList, getArticleListByCategory,  article, getArticle, createArticle, updateArticle, searchArticleList, deleteArticle,
//     totalItemCount, getPagination, pagination, currentPage, setCurrentPage }

// }, { persist: {
//     storage: sessionStorage }})
